package com.free.ping.module.mail;

import com.free.ping.api.mail.MailSender;
import com.free.ping.entity.Incident;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendMailImpl implements SendMail {

    protected final MailSender  mailSender;
    protected final ArgResolver argResolver;


    public SendMailImpl( MailSender mailSender, ArgResolver argResolver ) {
        this.mailSender  = mailSender;
        this.argResolver = argResolver;
    }


    @Override
    public void send( Incident incident ) {
        String         userTemplate;
        List< String > technicalRecipients = incident.getPing().getAlertTechnicalEmails();
        List< String > userRecipients      = incident.getPing().getAlertUserEmails();

        if ( incident.getType() == Incident.TYPE_DOWN_TIME ) {
            userTemplate = incident.getAt() == null
                    ? incident.getPing().getDownTimeDetectedTemplate()
                    : incident.getPing().getDownTimeEndedTemplate();

        } else {
            userTemplate = incident.getAt() == null
                    ? incident.getPing().getSlowDownDetectedTemplate()
                    : incident.getPing().getSlowDownEndedTemplate();
        }

        if ( userTemplate == null ) {
            return;
        }


        mailSender.send(
                null,
                technicalRecipients,
                ( incident.getAt() == null ? "DOWN " + incident.getOf().toString() : "UP " + incident.getAt().toString() ) + ( incident.getType() == Incident.TYPE_DOWN_TIME ? " DOWN TIME " : " SLOW DOWN " ) + incident.getPing().getTitle(),
                incident.getAt() == null
                        ? argResolver.resolveTemplate( "<html>Detected at $incident_of</html>", incident )
                        : argResolver.resolveTemplate( "<html>Detected at $incident_of<br/><br/>Solved at $incident_at</html>", incident )
        );

        userTemplate = argResolver.resolveTemplate( userTemplate, incident );
        String subject = argResolver.resolveSubject( incident );

        if ( userTemplate == null || subject == null ) {
            return;
        }

        mailSender.send(
                null,
                userRecipients,
                subject,
                userTemplate
        );
    }
}
