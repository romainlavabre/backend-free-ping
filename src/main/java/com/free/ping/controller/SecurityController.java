package com.free.ping.controller;


import com.free.ping.api.security.AuthenticationHandler;
import com.free.ping.api.security.JwtTokenHandler;
import com.free.ping.api.security.Security;
import com.free.ping.api.security.UserRepository;
import com.free.ping.configuration.environment.Variable;
import org.romainlavabre.encoder.Encoder;
import org.romainlavabre.environment.Environment;
import org.romainlavabre.request.Request;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@RestController
public class SecurityController {

    protected final JwtTokenHandler       jwtTokenHandler;
    protected final AuthenticationHandler authenticationHandler;
    protected final Request               request;
    protected final UserDetailsService    userDetailsService;
    protected final Environment           environment;
    protected final Security              security;
    protected final UserRepository        userRepository;


    public SecurityController(
            final JwtTokenHandler jwtTokenHandler,
            final AuthenticationHandler authenticationHandler,
            final Request request,
            final Environment environment,
            final Security security,
            @Qualifier( "userDetailsService" ) final UserDetailsService userDetailsService,
            final UserRepository userRepository ) {
        this.jwtTokenHandler       = jwtTokenHandler;
        this.authenticationHandler = authenticationHandler;
        this.request               = request;
        this.environment           = environment;
        this.userDetailsService    = userDetailsService;
        this.security              = security;
        this.userRepository        = userRepository;
    }


    @PostMapping( path = "/auth" )
    public ResponseEntity< Object > authenticate() {

        Authentication authentication = null;
        String         message        = null;

        try {
            authentication = this.authenticationHandler.authenticate( this.request );
        } catch ( final Throwable e ) {
            message = e.getMessage();
        }

        if ( authentication != null && authentication.isAuthenticated() ) {
            return ResponseEntity.ok().body( Map.of(
                    "access_token", this.jwtTokenHandler.createToken( this.userDetailsService.loadUserByUsername( ( String ) this.request.getParameter( "auth_username" ) ) ),
                    "token_type", "Bearer",
                    "expire_in", Integer.valueOf( this.environment.getEnv( Variable.JWT_LIFE_TIME ) )
            ) );
        }

        return ResponseEntity.status( HttpStatus.UNAUTHORIZED ).body( Map.of( "message", message ) );
    }


    @GetMapping( path = "/user/info" )
    public ResponseEntity< Map< String, Object > > userInfo() {

        return ResponseEntity.ok(
                Encoder.encode( this.userRepository.findByUsername( this.security.getUsername() ) )
        );
    }
}
