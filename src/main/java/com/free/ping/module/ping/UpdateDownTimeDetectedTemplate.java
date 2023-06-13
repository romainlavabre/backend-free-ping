package com.free.ping.module.ping;

import com.free.ping.api.crud.Update;
import com.free.ping.api.request.Request;
import com.free.ping.entity.Ping;
import com.free.ping.parameter.PingParameter;
import com.free.ping.repository.PingRepository;
import org.springframework.stereotype.Service;

@Service( "updatePingDownTimeDetectedTemplate" )
public class UpdateDownTimeDetectedTemplate implements Update< Ping > {
    protected final PingRepository pingRepository;


    public UpdateDownTimeDetectedTemplate( PingRepository pingRepository ) {
        this.pingRepository = pingRepository;
    }


    @Override
    public void update( Request request, Ping ping ) {
        String downTimeDetectedTemplate = request.getParameter( PingParameter.DOWN_TIME_DETECTED_TEMPLATE, String.class );

        ping.setDownTimeDetectedTemplate( downTimeDetectedTemplate );

        pingRepository.persist( ping );
    }
}
