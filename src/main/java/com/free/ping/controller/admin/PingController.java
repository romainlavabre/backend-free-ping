package com.free.ping.controller.admin;

import com.free.ping.api.crud.Create;
import com.free.ping.api.crud.Update;
import com.free.ping.api.json.Encoder;
import com.free.ping.api.request.Request;
import com.free.ping.api.storage.data.DataStorageHandler;
import com.free.ping.configuration.json.GroupType;
import com.free.ping.entity.Ping;
import com.free.ping.repository.PingRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping( path = "/admin/pings" )
@RestController( "AdminPingController" )
public class PingController {

    protected final Create< Ping >     createPing;
    protected final Update< Ping >     updatePingTitle;
    protected final Update< Ping >     updatePingPingUrl;
    protected final Update< Ping >     updatePingSlowDownSeconds;
    protected final Update< Ping >     updatePingVersionField;
    protected final Update< Ping >     updatePingAlertTechnicalEmails;
    protected final Update< Ping >     updatePingAlertTechnicalPhones;
    protected final Update< Ping >     updatePingAlertUserEmails;
    protected final PingRepository     pingRepository;
    protected final DataStorageHandler dataStorageHandler;
    protected final Request            request;


    public PingController(
            Create< Ping > createPing,
            Update< Ping > updatePingTitle,
            Update< Ping > updatePingPingUrl,
            Update< Ping > updatePingSlowDownSeconds,
            Update< Ping > updatePingVersionField,
            Update< Ping > updatePingAlertTechnicalEmails,
            Update< Ping > updatePingAlertTechnicalPhones,
            Update< Ping > updatePingAlertUserEmails,
            PingRepository pingRepository,
            DataStorageHandler dataStorageHandler,
            Request request ) {
        this.createPing                     = createPing;
        this.updatePingTitle                = updatePingTitle;
        this.updatePingPingUrl              = updatePingPingUrl;
        this.updatePingSlowDownSeconds      = updatePingSlowDownSeconds;
        this.updatePingVersionField         = updatePingVersionField;
        this.updatePingAlertTechnicalEmails = updatePingAlertTechnicalEmails;
        this.updatePingAlertTechnicalPhones = updatePingAlertTechnicalPhones;
        this.updatePingAlertUserEmails      = updatePingAlertUserEmails;
        this.pingRepository                 = pingRepository;
        this.dataStorageHandler             = dataStorageHandler;
        this.request                        = request;
    }


    @GetMapping( path = "/{id:[0-9]+}" )
    public ResponseEntity< Map< String, Object > > get( @PathVariable( "id" ) long id ) {
        Ping ping = pingRepository.findOrFail( id );

        return ResponseEntity.ok( Encoder.encode( ping, GroupType.ADMIN ) );
    }


    @Transactional
    @PostMapping
    public ResponseEntity< Map< String, Object > > create() {
        Ping ping = new Ping();

        createPing.create( request, ping );

        dataStorageHandler.save();

        return ResponseEntity
                .status( HttpStatus.CREATED )
                .body( Encoder.encode( ping ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/title" )
    public ResponseEntity< Map< String, Object > > updateTitle( @PathVariable( "id" ) long id ) {
        Ping ping = pingRepository.findOrFail( id );

        updatePingTitle.update( request, ping );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( ping ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/ping_url" )
    public ResponseEntity< Map< String, Object > > updatePingUrl( @PathVariable( "id" ) long id ) {
        Ping ping = pingRepository.findOrFail( id );

        updatePingPingUrl.update( request, ping );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( ping ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/version_field" )
    public ResponseEntity< Map< String, Object > > updateVersionField( @PathVariable( "id" ) long id ) {
        Ping ping = pingRepository.findOrFail( id );

        updatePingVersionField.update( request, ping );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( ping ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/slow_down_seconds" )
    public ResponseEntity< Map< String, Object > > updateSlowDownSeconds( @PathVariable( "id" ) long id ) {
        Ping ping = pingRepository.findOrFail( id );

        updatePingSlowDownSeconds.update( request, ping );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( ping ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/alert_technical_emails" )
    public ResponseEntity< Map< String, Object > > updateAlertTechnicalEmails( @PathVariable( "id" ) long id ) {
        Ping ping = pingRepository.findOrFail( id );

        updatePingAlertTechnicalEmails.update( request, ping );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( ping ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/alert_technical_phones" )
    public ResponseEntity< Map< String, Object > > updateAlertTechnicalPhones( @PathVariable( "id" ) long id ) {
        Ping ping = pingRepository.findOrFail( id );

        updatePingAlertTechnicalPhones.update( request, ping );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( ping ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/alert_user_emails" )
    public ResponseEntity< Map< String, Object > > updateAlertUserEmails( @PathVariable( "id" ) long id ) {
        Ping ping = pingRepository.findOrFail( id );

        updatePingAlertUserEmails.update( request, ping );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( ping ) );
    }
}
