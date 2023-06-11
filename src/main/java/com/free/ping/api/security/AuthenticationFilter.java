package com.free.ping.api.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service
public class AuthenticationFilter extends GenericFilterBean {

    private static final String                BEARER = "Bearer";
    @Autowired
    protected            Security              security;
    @Autowired
    protected            UserAccessResolver    userAccessResolver;
    @Autowired
    protected            AuthenticationHandler authenticationHandler;


    @Override
    public void doFilter( final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain chain ) throws IOException, ServletException {

        final HttpServletRequest  request  = ( HttpServletRequest ) servletRequest;
        final HttpServletResponse response = ( HttpServletResponse ) servletResponse;

        final Optional< String > token = Optional.ofNullable( request.getHeader( HttpHeaders.AUTHORIZATION ) );

        if ( token.isPresent() && token.get().startsWith( AuthenticationFilter.BEARER ) ) {

            final String bearerToken = token.get().substring( AuthenticationFilter.BEARER.length() + 1 );

            try {
                User           user           = userAccessResolver.getUser( bearerToken );
                Authentication authentication = (( AuthenticationHandlerImpl ) this.authenticationHandler).getAuthentication( user );
                SecurityContextHolder.getContext().setAuthentication( authentication );
                this.hydrateSecurityService( user );
            } catch ( final ExpiredJwtException exception ) {
                response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "error.jwt.expired" );
                return;
            } catch ( final JwtException exception ) {
                response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "error.jwt.invalid" );
                return;
            }
        }

        chain.doFilter( servletRequest, servletResponse );

        SecurityContextHolder.getContext().setAuthentication( null );
    }


    private void hydrateSecurityService( User user ) {
        final SecurityImpl securityImpl = ( SecurityImpl ) this.security;

        securityImpl.hydrate( user );
    }
}
