package com.free.ping.module.ping;

import org.romainlavabre.crud.Update;
import org.romainlavabre.history.HistoryHandler;
import org.romainlavabre.request.Request;
import com.free.ping.entity.Ping;
import com.free.ping.parameter.PingParameter;
import com.free.ping.property.PingProperty;
import com.free.ping.repository.PingRepository;
import org.springframework.stereotype.Service;

@Service( "updatePingInterval" )
public class UpdateInterval implements Update< Ping > {
    protected final PingRepository pingRepository;
    protected final HistoryHandler historyHandler;


    public UpdateInterval( PingRepository pingRepository, HistoryHandler historyHandler ) {
        this.pingRepository = pingRepository;
        this.historyHandler = historyHandler;
    }


    @Override
    public void update( Request request, Ping ping ) {
        Long interval = request.getParameter( PingParameter.INTERVAL, Long.class );

        ping.setInterval( interval );

        historyHandler.update( ping, PingProperty.INTERVAL );

        pingRepository.persist( ping );
    }
}
