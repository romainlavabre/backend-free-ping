package com.free.ping.module.ping;

import org.romainlavabre.crud.Update;
import org.romainlavabre.request.Request;
import com.free.ping.entity.Ping;
import com.free.ping.parameter.PingParameter;
import com.free.ping.repository.PingRepository;
import org.springframework.stereotype.Service;

@Service( "updatePingSlowDownEndedTemplate" )
public class UpdateSlowDownEndedTemplate implements Update< Ping > {
    protected final PingRepository pingRepository;


    public UpdateSlowDownEndedTemplate( PingRepository pingRepository ) {
        this.pingRepository = pingRepository;
    }


    @Override
    public void update( Request request, Ping ping ) {
        String slowDownEndedTemplate = request.getParameter( PingParameter.SLOW_DOWN_ENDED_TEMPLATE, String.class );

        ping.setSlowDownEndedTemplate( slowDownEndedTemplate );

        pingRepository.persist( ping );
    }
}
