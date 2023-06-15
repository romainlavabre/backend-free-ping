package com.free.ping.controller.admin;

import com.free.ping.api.mail.MailSender;
import com.free.ping.entity.Incident;
import com.free.ping.entity.Ping;
import com.free.ping.module.mail.SendMail;
import com.free.ping.repository.PingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@RestController( "AdminTestController" )
@RequestMapping( path = "/admin/tests" )
public class TestController {

    protected final MailSender     mailSender;
    protected final PingRepository pingRepository;
    protected final SendMail       sendMail;


    public TestController( MailSender mailSender, PingRepository pingRepository, SendMail sendMail ) {
        this.mailSender     = mailSender;
        this.pingRepository = pingRepository;
        this.sendMail       = sendMail;
    }


    @PostMapping( path = "/mail/{recipient}" )
    public ResponseEntity< Map< String, Boolean > > mail( @PathVariable( "recipient" ) String recipient ) {
        boolean result = mailSender.send( null, recipient, "FREE PING TEST", "This message is test from free ping" );

        return ResponseEntity.ok( Map.of(
                "result", result
        ) );
    }


    @PostMapping( path = "/mail/down_time/{recipient}/{pingId}" )
    public ResponseEntity< Map< String, Boolean > > mailDownTime( @PathVariable( "recipient" ) String recipient, @PathVariable( "pingId" ) long pingId ) {
        Ping ping = pingRepository.findOrFail( pingId );

        ping
                .setAlertTechnicalEmails( List.of( recipient ) )
                .setAlertUserEmails( List.of( recipient ) );

        Incident incident = new Incident();
        incident.setType( Incident.TYPE_DOWN_TIME )
                .setOf( ZonedDateTime.now( ZoneOffset.UTC ) )
                .setPing( ping );

        sendMail.send( incident );

        incident.setAt( ZonedDateTime.now( ZoneOffset.UTC ) );

        sendMail.send( incident );

        return ResponseEntity.ok( Map.of(
                "result", true
        ) );
    }


    @PostMapping( path = "/mail/slow_down/{recipient}/{pingId}" )
    public ResponseEntity< Map< String, Boolean > > mailSlowDown( @PathVariable( "recipient" ) String recipient, @PathVariable( "pingId" ) long pingId ) {
        Ping ping = pingRepository.findOrFail( pingId );

        ping
                .setAlertTechnicalEmails( List.of( recipient ) )
                .setAlertUserEmails( List.of( recipient ) );

        Incident incident = new Incident();
        incident.setType( Incident.TYPE_SLOW_DOWN )
                .setOf( ZonedDateTime.now( ZoneOffset.UTC ) )
                .setPing( ping );

        sendMail.send( incident );

        incident.setAt( ZonedDateTime.now( ZoneOffset.UTC ) );

        sendMail.send( incident );

        return ResponseEntity.ok( Map.of(
                "result", true
        ) );
    }
}
