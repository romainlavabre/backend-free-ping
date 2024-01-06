package com.free.ping.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.romainlavabre.request.Request;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service
public class AuthenticationHandlerImpl implements AuthenticationHandler {

    private final AuthenticationManager authenticationManager;


    public AuthenticationHandlerImpl( final AuthenticationManager authenticationManager ) {
        this.authenticationManager = authenticationManager;
    }


    public Authentication getAuthentication( User user ) {
        return new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user,
                user.getAuthorities()
        );
    }


    @Override
    public Authentication getAuthentication( Jws< Claims > token ) {
        return null;
    }


    @Override
    public Authentication authenticate( final Request request ) {

        final UsernamePasswordAuthenticationToken usernameAuthentication =
                new UsernamePasswordAuthenticationToken( request.getParameter( "auth_username" ), request.getParameter( "auth_password" ) );

        return this.authenticationManager.authenticate( usernameAuthentication );
    }
}
