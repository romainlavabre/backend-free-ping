package com.free.ping.module.ping;

import org.romainlavabre.crud.Update;
import org.romainlavabre.request.Request;
import com.free.ping.entity.Ping;
import com.free.ping.parameter.PingParameter;
import com.free.ping.repository.PingRepository;
import org.springframework.stereotype.Service;

@Service( "updatePingDownTimeEndedTemplate" )
public class UpdateDownTimeEndedTemplate implements Update< Ping > {
    protected final PingRepository pingRepository;


    public UpdateDownTimeEndedTemplate( PingRepository pingRepository ) {
        this.pingRepository = pingRepository;
    }


    @Override
    public void update( Request request, Ping ping ) {
        String downTimeEndedTemplate = request.getParameter( PingParameter.DOWN_TIME_ENDED_TEMPLATE, String.class );

        ping.setDownTimeEndedTemplate( downTimeEndedTemplate );

        pingRepository.persist( ping );
    }
}
