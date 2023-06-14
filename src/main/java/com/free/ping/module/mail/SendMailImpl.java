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
        String         template;
        List< String > recipients = incident.getPing().getAlertTechnicalEmails();

        if ( incident.getType() == Incident.TYPE_DOWN_TIME ) {
            template = incident.getAt() == null
                    ? incident.getPing().getDownTimeDetectedTemplate()
                    : incident.getPing().getDownTimeEndedTemplate();

        } else {
            template = incident.getAt() == null
                    ? incident.getPing().getSlowDownDetectedTemplate()
                    : incident.getPing().getSlowDownEndedTemplate();
        }

        if ( template == null ) {
            return;
        }

        template = argResolver.resolveTemplate( template, incident );


    }
}
