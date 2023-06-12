package com.free.ping.controller.admin;

import com.free.ping.api.crud.Create;
import com.free.ping.api.crud.Update;
import com.free.ping.api.json.Encoder;
import com.free.ping.api.request.Request;
import com.free.ping.api.storage.data.DataStorageHandler;
import com.free.ping.configuration.json.GroupType;
import com.free.ping.entity.Incident;
import com.free.ping.entity.Ping;
import com.free.ping.repository.IncidentRepository;
import com.free.ping.repository.PingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping( path = "/admin/incidents" )
@RestController( "AdminIncidentController" )
public class IncidentController {

    protected final Create< Incident > createIncident;
    protected final Update< Incident > updateIncidentAt;
    protected final IncidentRepository incidentRepository;
    protected final PingRepository     pingRepository;
    protected final DataStorageHandler dataStorageHandler;
    protected final Request            request;


    public IncidentController(
            Create< Incident > createIncident,
            Update< Incident > updateIncidentAt,
            IncidentRepository incidentRepository,
            PingRepository pingRepository,
            DataStorageHandler dataStorageHandler,
            Request request ) {
        this.createIncident     = createIncident;
        this.updateIncidentAt   = updateIncidentAt;
        this.incidentRepository = incidentRepository;
        this.pingRepository     = pingRepository;
        this.dataStorageHandler = dataStorageHandler;
        this.request            = request;
    }


    @GetMapping( path = "/{id:[0-9]+}" )
    public ResponseEntity< Map< String, Object > > get( @PathVariable( "id" ) long id ) {
        Incident incident = incidentRepository.findOrFail( id );

        return ResponseEntity.ok( Encoder.encode( incident, GroupType.ADMIN ) );
    }


    @GetMapping( path = "/by/ping/{id:[0-9]+}" )
    public ResponseEntity< List< Map< String, Object > > > getByPing( @PathVariable( "id" ) long id ) {
        Ping             ping      = pingRepository.findOrFail( id );
        List< Incident > incidents = incidentRepository.findAllByPing( ping );

        return ResponseEntity.ok( Encoder.encode( incidents, GroupType.ADMIN ) );
    }

}
