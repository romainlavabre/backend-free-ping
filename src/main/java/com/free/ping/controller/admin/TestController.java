package com.free.ping.controller.admin;

import com.free.ping.api.mail.MailSender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController( "AdminTestController" )
@RequestMapping( path = "/admin/tests" )
public class TestController {

    protected final MailSender mailSender;


    public TestController( MailSender mailSender ) {
        this.mailSender = mailSender;
    }


    @PostMapping( path = "/mail/{recipient}" )
    public ResponseEntity< Map< String, Boolean > > mail( @PathVariable( "recipient" ) String recipient ) {
        boolean result = mailSender.send( null, recipient, "FREE PING TEST", "This message is test from free ping" );

        return ResponseEntity.ok( Map.of(
                "result", result
        ) );
    }
}
