package com.free.ping.module.ping;

import org.romainlavabre.crud.Update;
import org.romainlavabre.history.HistoryHandler;
import org.romainlavabre.request.Request;
import com.free.ping.entity.Ping;
import com.free.ping.parameter.PingParameter;
import com.free.ping.property.PingProperty;
import com.free.ping.repository.PingRepository;
import org.springframework.stereotype.Service;

@Service( "updatePingPingUrl" )
public class UpdatePingUrl implements Update< Ping > {
    protected final PingRepository pingRepository;
    protected final HistoryHandler historyHandler;


    public UpdatePingUrl( PingRepository pingRepository, HistoryHandler historyHandler ) {
        this.pingRepository = pingRepository;
        this.historyHandler = historyHandler;
    }


    @Override
    public void update( Request request, Ping ping ) {
        String pingUrl = request.getParameter( PingParameter.PING_URL, String.class );

        ping.setPingUrl( pingUrl );

        historyHandler.update( ping, PingProperty.PING_URL );

        pingRepository.persist( ping );
    }
}
