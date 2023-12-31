package com.free.ping.module.ping;

import com.free.ping.entity.Ping;
import com.free.ping.parameter.PingParameter;
import com.free.ping.repository.PingRepository;
import org.romainlavabre.history.HistoryHandler;
import org.romainlavabre.request.Request;
import org.springframework.stereotype.Service;

import java.util.List;

@Service( "createPing" )
public class Create implements org.romainlavabre.crud.Create< Ping > {
    protected final PingRepository pingRepository;
    protected final HistoryHandler historyHandler;


    public Create( PingRepository pingRepository, HistoryHandler historyHandler ) {
        this.pingRepository = pingRepository;
        this.historyHandler = historyHandler;
    }


    @Override
    public void create( Request request, Ping ping ) {
        String         title                = request.getParameter( PingParameter.TITLE, String.class );
        String         pingUrl              = request.getParameter( PingParameter.PING_URL, String.class );
        Long           slowDownSeconds      = request.getParameter( PingParameter.SLOW_DOWN_SECONDS, Long.class );
        Long           interval             = request.getParameter( PingParameter.INTERVAL, Long.class );
        List< Object > alertTechnicalEmails = request.getParameters( PingParameter.ALERT_TECHNICAL_EMAILS );
        List< Object > alertTechnicalPhones = request.getParameters( PingParameter.ALERT_TECHNICAL_PHONES );
        List< Object > alertUserEmails      = request.getParameters( PingParameter.ALERT_USER_EMAILS );

        ping
                .setTitle( title )
                .setPingUrl( pingUrl )
                .setSlowDownSeconds( slowDownSeconds )
                .setInterval( interval )
                .setAlertTechnicalEmails( alertTechnicalEmails )
                .setAlertTechnicalPhones( alertTechnicalPhones )
                .setAlertUserEmails( alertUserEmails );

        historyHandler.create( ping );

        pingRepository.persist( ping );
    }
}
