package com.free.ping.module.ping;

import com.free.ping.api.crud.Update;
import com.free.ping.api.request.Request;
import com.free.ping.entity.Ping;
import com.free.ping.parameter.PingParameter;
import com.free.ping.repository.PingRepository;
import org.springframework.stereotype.Service;

@Service( "updatePingSlowDownUserSubject" )
public class UpdateSlowDownUserSubject implements Update< Ping > {
    protected final PingRepository pingRepository;


    public UpdateSlowDownUserSubject( PingRepository pingRepository ) {
        this.pingRepository = pingRepository;
    }


    @Override
    public void update( Request request, Ping ping ) {
        String slowDownUserSubject = request.getParameter( PingParameter.SLOW_DOWN_USER_SUBJECT, String.class );

        ping.setSlowDownUserSubject( slowDownUserSubject );

        pingRepository.persist( ping );
    }
}
