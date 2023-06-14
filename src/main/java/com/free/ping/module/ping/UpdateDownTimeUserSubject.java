package com.free.ping.module.ping;

import com.free.ping.api.crud.Update;
import com.free.ping.api.request.Request;
import com.free.ping.entity.Ping;
import com.free.ping.parameter.PingParameter;
import com.free.ping.repository.PingRepository;
import org.springframework.stereotype.Service;

@Service( "updatePingDownTimeUserSubject" )
public class UpdateDownTimeUserSubject implements Update< Ping > {
    protected final PingRepository pingRepository;


    public UpdateDownTimeUserSubject( PingRepository pingRepository ) {
        this.pingRepository = pingRepository;
    }


    @Override
    public void update( Request request, Ping ping ) {
        String downTimeUserSubject = request.getParameter( PingParameter.DOWN_TIME_USER_SUBJECT, String.class );

        ping.setDownTimeUserSubject( downTimeUserSubject );

        pingRepository.persist( ping );
    }
}
