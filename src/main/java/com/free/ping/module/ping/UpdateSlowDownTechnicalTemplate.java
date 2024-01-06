package com.free.ping.module.ping;

import org.romainlavabre.crud.Update;
import org.romainlavabre.request.Request;
import com.free.ping.entity.Ping;
import com.free.ping.parameter.PingParameter;
import com.free.ping.repository.PingRepository;
import org.springframework.stereotype.Service;

@Service( "updatePingSlowDownTechnicalTemplate" )
public class UpdateSlowDownTechnicalTemplate implements Update< Ping > {
    protected final PingRepository pingRepository;


    public UpdateSlowDownTechnicalTemplate( PingRepository pingRepository ) {
        this.pingRepository = pingRepository;
    }


    @Override
    public void update( Request request, Ping ping ) {
        String slowDownTechnicalTemplate = request.getParameter( PingParameter.SLOW_DOWN_TECHNICAL_TEMPLATE, String.class );

        ping.setSlowDownTechnicalTemplate( slowDownTechnicalTemplate );

        pingRepository.persist( ping );
    }
}
