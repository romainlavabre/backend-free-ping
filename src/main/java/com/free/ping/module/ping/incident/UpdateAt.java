package com.free.ping.module.ping.incident;

import com.free.ping.api.crud.Update;
import com.free.ping.api.history.HistoryHandler;
import com.free.ping.api.request.Request;
import com.free.ping.entity.Incident;
import com.free.ping.parameter.IncidentParameter;
import com.free.ping.property.IncidentProperty;
import com.free.ping.repository.IncidentRepository;
import org.springframework.stereotype.Service;

@Service( "updateIncidentAt" )
public class UpdateAt implements Update< Incident > {
    protected final IncidentRepository incidentRepository;
    protected final HistoryHandler     historyHandler;


    public UpdateAt( IncidentRepository incidentRepository, HistoryHandler historyHandler ) {
        this.incidentRepository = incidentRepository;
        this.historyHandler     = historyHandler;
    }


    @Override
    public void update( Request request, Incident incident ) {
        String at = request.getParameter( IncidentParameter.AT, String.class );

        incident.setAt( at );

        historyHandler.update( incident, IncidentProperty.AT );
    }
}
