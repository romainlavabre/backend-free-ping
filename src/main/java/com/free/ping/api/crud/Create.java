package com.free.ping.api.crud;

import com.free.ping.api.request.Request;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface Create< E > {
    void create( Request request, E entity );
}
