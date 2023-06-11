package com.free.ping.api.security;

import com.free.ping.api.request.Request;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.core.Authentication;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface AuthenticationHandler {
    Authentication getAuthentication( Jws< Claims > token );

    Authentication authenticate( final Request request );
}
