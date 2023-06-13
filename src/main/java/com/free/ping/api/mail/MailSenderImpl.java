package com.free.ping.api.mail;

import com.free.ping.api.environment.Environment;
import com.free.ping.configuration.environment.Variable;
import com.free.ping.exception.HttpInternalServerErrorException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service( "mailSender" )
public class MailSenderImpl implements MailSender {
    protected final MailSender  mailSenderMailgun;
    protected final MailSender  mailSenderGmail;
    protected final Environment environment;


    public MailSenderImpl( MailSender mailSenderMailgun, MailSender mailSenderGmail, Environment environment ) {
        this.mailSenderMailgun = mailSenderMailgun;
        this.mailSenderGmail   = mailSenderGmail;
        this.environment       = environment;
    }


    @Override
    public boolean send( String from, List< String > to, String subject, String message ) {
        return getInstance().send( from, to, subject, message );
    }


    @Override
    public boolean send( String from, List< String > to, String subject, String message, List< File > files ) {
        return getInstance().send( from, to, subject, message, files );
    }


    @Override
    public boolean send( String from, String to, String subject, String message ) {
        return getInstance().send( from, to, subject, message );
    }


    @Override
    public boolean send( String from, String to, String subject, String message, List< File > files ) {
        return getInstance().send( from, to, subject, message, files );
    }


    protected MailSender getInstance() {
        if ( environment.getEnv( Variable.GMAIL_USERNAME ) != null
                && !environment.getEnv( Variable.GMAIL_USERNAME ).isBlank() ) {
            return mailSenderGmail;
        }

        if ( environment.getEnv( Variable.MAILGUN_DOMAIN ) != null
                && !environment.getEnv( Variable.MAILGUN_DOMAIN ).isBlank() ) {
            return mailSenderMailgun;
        }

        throw new HttpInternalServerErrorException( "MAIL_CONFIGURATION_NOT_FOUND" );
    }
}
