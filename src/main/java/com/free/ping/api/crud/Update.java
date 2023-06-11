package com.free.ping.api.crud;

import com.free.ping.api.request.Request;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface Update< E > {
    void update( Request request, E entity );
}
