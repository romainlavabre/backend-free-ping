package com.free.ping.controller.admin;

import com.free.ping.api.mail.MailSender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController( "AdminTestController" )
@RequestMapping( path = "/admin/tests" )
public class TestController {

    protected final MailSender mailSender;


    public TestController( MailSender mailSender ) {
        this.mailSender = mailSender;
    }


    @PostMapping( path = "/mail/{recipient}" )
    public ResponseEntity< Void > mail( @PathVariable( "recipient" ) String recipient ) {
        mailSender.send( null, recipient, "TEST", "This message is test from free ping" );

        return ResponseEntity.noContent().build();
    }
}
