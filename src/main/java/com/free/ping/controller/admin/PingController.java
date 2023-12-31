package com.free.ping.controller.admin;

import com.free.ping.configuration.json.GroupType;
import com.free.ping.entity.Ping;
import com.free.ping.repository.PingRepository;
import jakarta.transaction.Transactional;
import org.romainlavabre.crud.Create;
import org.romainlavabre.crud.Update;
import org.romainlavabre.database.DataStorageHandler;
import org.romainlavabre.encoder.Encoder;
import org.romainlavabre.request.Request;
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
    protected final Update< Ping >     updatePingInterval;
    protected final Update< Ping >     updatePingAlertTechnicalEmails;
    protected final Update< Ping >     updatePingAlertTechnicalPhones;
    protected final Update< Ping >     updatePingAlertUserEmails;
    protected final Update< Ping >     updatePingDownTimeDetectedTemplate;
    protected final Update< Ping >     updatePingDownTimeEndedTemplate;
    protected final Update< Ping >     updatePingSlowDownDetectedTemplate;
    protected final Update< Ping >     updatePingSlowDownEndedTemplate;
    protected final Update< Ping >     updatePingDownTimeTechnicalTemplate;
    protected final Update< Ping >     updatePingSlowDownTechnicalTemplate;
    protected final Update< Ping >     updatePingDownTimeUserSubject;
    protected final Update< Ping >     updatePingSlowDownUserSubject;
    protected final PingRepository     pingRepository;
    protected final DataStorageHandler dataStorageHandler;
    protected final Request            request;


    public PingController(
            Create< Ping > createPing,
            Update< Ping > updatePingTitle,
            Update< Ping > updatePingPingUrl,
            Update< Ping > updatePingSlowDownSeconds,
            Update< Ping > updatePingInterval,
            Update< Ping > updatePingAlertTechnicalEmails,
            Update< Ping > updatePingAlertTechnicalPhones,
            Update< Ping > updatePingAlertUserEmails,
            Update< Ping > updatePingDownTimeDetectedTemplate,
            Update< Ping > updatePingDownTimeEndedTemplate,
            Update< Ping > updatePingSlowDownDetectedTemplate,
            Update< Ping > updatePingSlowDownEndedTemplate,
            Update< Ping > updatePingDownTimeTechnicalTemplate,
            Update< Ping > updatePingSlowDownTechnicalTemplate,
            Update< Ping > updatePingDownTimeUserSubject,
            Update< Ping > updatePingSlowDownUserSubject,
            PingRepository pingRepository,
            DataStorageHandler dataStorageHandler,
            Request request ) {
        this.createPing                          = createPing;
        this.updatePingTitle                     = updatePingTitle;
        this.updatePingPingUrl                   = updatePingPingUrl;
        this.updatePingSlowDownSeconds           = updatePingSlowDownSeconds;
        this.updatePingInterval                  = updatePingInterval;
        this.updatePingAlertTechnicalEmails      = updatePingAlertTechnicalEmails;
        this.updatePingAlertTechnicalPhones      = updatePingAlertTechnicalPhones;
        this.updatePingAlertUserEmails           = updatePingAlertUserEmails;
        this.updatePingDownTimeDetectedTemplate  = updatePingDownTimeDetectedTemplate;
        this.updatePingDownTimeEndedTemplate     = updatePingDownTimeEndedTemplate;
        this.updatePingSlowDownDetectedTemplate  = updatePingSlowDownDetectedTemplate;
        this.updatePingSlowDownEndedTemplate     = updatePingSlowDownEndedTemplate;
        this.updatePingDownTimeTechnicalTemplate = updatePingDownTimeTechnicalTemplate;
        this.updatePingSlowDownTechnicalTemplate = updatePingSlowDownTechnicalTemplate;
        this.updatePingDownTimeUserSubject       = updatePingDownTimeUserSubject;
        this.updatePingSlowDownUserSubject       = updatePingSlowDownUserSubject;
        this.pingRepository                      = pingRepository;
        this.dataStorageHandler                  = dataStorageHandler;
        this.request                             = request;
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
    @PatchMapping( path = "/{id:[0-9]+}/slow_down_seconds" )
    public ResponseEntity< Map< String, Object > > updateSlowDownSeconds( @PathVariable( "id" ) long id ) {
        Ping ping = pingRepository.findOrFail( id );

        updatePingSlowDownSeconds.update( request, ping );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( ping ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/interval" )
    public ResponseEntity< Map< String, Object > > updateInterval( @PathVariable( "id" ) long id ) {
        Ping ping = pingRepository.findOrFail( id );

        updatePingInterval.update( request, ping );

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


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/down_time_detected_template" )
    public ResponseEntity< Map< String, Object > > updateDownTimeDetectedTemplate( @PathVariable( "id" ) long id ) {
        Ping ping = pingRepository.findOrFail( id );

        updatePingDownTimeDetectedTemplate.update( request, ping );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( ping ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/down_time_ended_template" )
    public ResponseEntity< Map< String, Object > > updateDownTimeEndedTemplate( @PathVariable( "id" ) long id ) {
        Ping ping = pingRepository.findOrFail( id );

        updatePingDownTimeEndedTemplate.update( request, ping );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( ping ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/slow_down_detected_template" )
    public ResponseEntity< Map< String, Object > > updateSlowDownDetectedTemplate( @PathVariable( "id" ) long id ) {
        Ping ping = pingRepository.findOrFail( id );

        updatePingSlowDownDetectedTemplate.update( request, ping );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( ping ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/slow_down_ended_template" )
    public ResponseEntity< Map< String, Object > > updateSlowDownEndedTemplate( @PathVariable( "id" ) long id ) {
        Ping ping = pingRepository.findOrFail( id );

        updatePingSlowDownEndedTemplate.update( request, ping );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( ping ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/down_time_technical_template" )
    public ResponseEntity< Map< String, Object > > updateDownTimeTechnicalTemplate( @PathVariable( "id" ) long id ) {
        Ping ping = pingRepository.findOrFail( id );

        updatePingDownTimeTechnicalTemplate.update( request, ping );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( ping ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/slow_down_technical_template" )
    public ResponseEntity< Map< String, Object > > updateSlowDownTechnicalTemplate( @PathVariable( "id" ) long id ) {
        Ping ping = pingRepository.findOrFail( id );

        updatePingSlowDownTechnicalTemplate.update( request, ping );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( ping ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/down_time_user_subject" )
    public ResponseEntity< Map< String, Object > > updateDownTimeUserSubject( @PathVariable( "id" ) long id ) {
        Ping ping = pingRepository.findOrFail( id );

        updatePingDownTimeUserSubject.update( request, ping );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( ping ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/slow_down_user_subject" )
    public ResponseEntity< Map< String, Object > > updateSlowDownUserSubject( @PathVariable( "id" ) long id ) {
        Ping ping = pingRepository.findOrFail( id );

        updatePingSlowDownUserSubject.update( request, ping );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( ping ) );
    }
}
