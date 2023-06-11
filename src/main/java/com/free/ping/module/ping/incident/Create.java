package com.free.ping.module.ping.incident;

import com.free.ping.api.history.HistoryHandler;
import com.free.ping.api.request.Request;
import com.free.ping.entity.Incident;
import com.free.ping.parameter.IncidentParameter;
import com.free.ping.repository.IncidentRepository;
import com.free.ping.repository.PingRepository;
import org.springframework.stereotype.Service;

@Service( "createIncident" )
public class Create implements com.free.ping.api.crud.Create< Incident > {
    protected final IncidentRepository incidentRepository;
    protected final HistoryHandler     historyHandler;
    protected final PingRepository     pingRepository;


    public Create( IncidentRepository incidentRepository, HistoryHandler historyHandler, PingRepository pingRepository ) {
        this.incidentRepository = incidentRepository;
        this.historyHandler     = historyHandler;
        this.pingRepository     = pingRepository;
    }


    @Override
    public void create( Request request, Incident incident ) {
        String of     = request.getParameter( IncidentParameter.OF, String.class );
        Byte   type   = request.getParameter( IncidentParameter.TYPE, Byte.class );
        Long   pingId = request.getParameter( IncidentParameter.PING, Long.class );


        incident.setOf( of )
                .setType( type )
                .setPing( pingRepository.findOrFail( pingId ) );

        historyHandler.create( incident );

        incidentRepository.persist( incident );
    }
}
