package com.free.ping.module.ping;

import com.free.ping.api.crud.Update;
import com.free.ping.api.history.HistoryHandler;
import com.free.ping.api.request.Request;
import com.free.ping.entity.Ping;
import com.free.ping.parameter.PingParameter;
import com.free.ping.property.PingProperty;
import com.free.ping.repository.PingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service( "updatePingAlertTechnicalPhones" )
public class UpdateAlertTechnicalPhones implements Update< Ping > {
    protected final PingRepository pingRepository;
    protected final HistoryHandler historyHandler;


    public UpdateAlertTechnicalPhones( PingRepository pingRepository, HistoryHandler historyHandler ) {
        this.pingRepository = pingRepository;
        this.historyHandler = historyHandler;
    }


    @Override
    public void update( Request request, Ping ping ) {
        List< Object > alertTechnicalPhones = request.getParameters( PingParameter.ALERT_TECHNICAL_PHONES );

        ping.setAlertTechnicalPhones( alertTechnicalPhones );

        historyHandler.update( ping, PingProperty.ALERT_TECHNICAL_PHONES );

        pingRepository.persist( ping );
    }
}
