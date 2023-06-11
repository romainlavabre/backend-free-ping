package com.free.ping.api.pagination;

import com.free.ping.api.pagination.exception.NotSupportedKey;
import com.free.ping.api.pagination.exception.NotSupportedOperator;
import com.free.ping.api.pagination.exception.NotSupportedValue;
import com.free.ping.api.request.Request;

import java.util.List;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface PaginationHandler {
    /**
     * @param request The request
     * @param dtoType The DTO to consume
     * @param view    The view name
     * @param <T>     The DTO type
     * @return Pagination object to encode
     * @throws NotSupportedOperator If an operator is invalid
     * @throws NotSupportedKey      If an key if invalid (Prevent SQL injection)
     * @throws NotSupportedValue    If a value is invalid (Prevent SQL injection)
     */
    < T > Pagination getResult( Request request, Class< T > dtoType, String view )
            throws NotSupportedOperator, NotSupportedKey, NotSupportedValue;


    boolean hasBeenUpdated( Request request, List< String > followedTables );
}
