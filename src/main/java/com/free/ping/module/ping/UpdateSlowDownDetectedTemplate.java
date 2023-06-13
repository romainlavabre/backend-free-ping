package com.free.ping.module.ping;

import com.free.ping.api.crud.Update;
import com.free.ping.api.request.Request;
import com.free.ping.entity.Ping;
import com.free.ping.parameter.PingParameter;
import com.free.ping.repository.PingRepository;
import org.springframework.stereotype.Service;

@Service( "updatePingSlowDownDetectedTemplate" )
public class UpdateSlowDownDetectedTemplate implements Update< Ping > {
    protected final PingRepository pingRepository;


    public UpdateSlowDownDetectedTemplate( PingRepository pingRepository ) {
        this.pingRepository = pingRepository;
    }


    @Override
    public void update( Request request, Ping ping ) {
        String slowDownDetectedTemplate = request.getParameter( PingParameter.SLOW_DOWN_DETECTED_TEMPLATE, String.class );

        ping.setSlowDownDetectedTemplate( slowDownDetectedTemplate );

        pingRepository.persist( ping );
    }
}
