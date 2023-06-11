package com.free.ping.module.ping;

import com.free.ping.api.crud.Update;
import com.free.ping.api.history.HistoryHandler;
import com.free.ping.api.request.Request;
import com.free.ping.entity.Ping;
import com.free.ping.parameter.PingParameter;
import com.free.ping.property.PingProperty;
import com.free.ping.repository.PingRepository;
import org.springframework.stereotype.Service;

@Service( "updatePingSlowDownSeconds" )
public class UpdateSlowDownSeconds implements Update< Ping > {
    protected final PingRepository pingRepository;
    protected final HistoryHandler historyHandler;


    public UpdateSlowDownSeconds( PingRepository pingRepository, HistoryHandler historyHandler ) {
        this.pingRepository = pingRepository;
        this.historyHandler = historyHandler;
    }


    @Override
    public void update( Request request, Ping ping ) {
        Long slowDownSeconds = request.getParameter( PingParameter.SLOW_DOWN_SECONDS, Long.class );

        ping.setSlowDownSeconds( slowDownSeconds );

        historyHandler.update( ping, PingProperty.SLOW_DOWN_SECONDS );

        pingRepository.persist( ping );
    }
}
