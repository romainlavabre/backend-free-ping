package com.free.ping.entity;

import com.free.ping.configuration.response.Message;
import com.free.ping.entity.converter.JsonArrayColumn;
import com.free.ping.entity.converter.JsonArrayColumnConverter;
import com.free.ping.exception.HttpUnprocessableEntityException;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Ping {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;

    @Column( nullable = false, length = 600 )
    private String title;

    @Column( nullable = false, length = 600 )
    private String pingUrl;

    private long slowDownSeconds;

    @Column( name = "`interval`", nullable = false )
    private long interval;

    @Column( columnDefinition = "TEXT" )
    private String downTimeDetectedTemplate;

    @Column( columnDefinition = "TEXT" )
    private String downTimeEndedTemplate;

    @Column( columnDefinition = "TEXT" )
    private String slowDownDetectedTemplate;

    @Column( columnDefinition = "TEXT" )
    private String slowDownEndedTemplate;

    @Convert( converter = JsonArrayColumnConverter.class )
    @Column( columnDefinition = "JSON" )
    private JsonArrayColumn< String > alertTechnicalEmails;

    @Convert( converter = JsonArrayColumnConverter.class )
    @Column( columnDefinition = "JSON" )
    private JsonArrayColumn< String > alertTechnicalPhones;

    @Convert( converter = JsonArrayColumnConverter.class )
    @Column( columnDefinition = "JSON" )
    private JsonArrayColumn< String > alertUserEmails;


    public Ping() {
        alertTechnicalEmails = new JsonArrayColumn<>();
        alertTechnicalPhones = new JsonArrayColumn<>();
        alertUserEmails      = new JsonArrayColumn<>();
    }


    public long getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }


    public Ping setTitle( String title ) {
        if ( title == null || title.isBlank() ) {
            throw new HttpUnprocessableEntityException( Message.PING_TITLE_REQUIRED );
        }

        this.title = title;

        return this;
    }


    public String getPingUrl() {
        return pingUrl;
    }


    public Ping setPingUrl( String pingUrl ) {
        if ( pingUrl == null || pingUrl.isBlank() ) {
            throw new HttpUnprocessableEntityException( Message.PING_PING_URL_REQUIRED );
        }

        this.pingUrl = pingUrl;

        return this;
    }


    public long getSlowDownSeconds() {
        return slowDownSeconds;
    }


    public Ping setSlowDownSeconds( Long slowDownSeconds ) {
        if ( slowDownSeconds == null ) {
            throw new HttpUnprocessableEntityException( Message.PING_SLOW_DOWN_SECONDS_REQUIRED );
        }

        if ( slowDownSeconds < 1 ) {
            throw new HttpUnprocessableEntityException( Message.PING_SLOW_DOWN_SECONDS_INVALID );
        }

        this.slowDownSeconds = slowDownSeconds;

        return this;
    }


    public long getInterval() {
        return interval;
    }


    public Ping setInterval( Long interval ) {
        if ( interval == null ) {
            throw new HttpUnprocessableEntityException( Message.PING_INTERVAL_REQUIRED );
        }

        if ( interval < 10 ) {
            throw new HttpUnprocessableEntityException( Message.PING_INTERVAL_TOO_SHORT );
        }

        this.interval = interval;

        return this;
    }


    public String getDownTimeDetectedTemplate() {
        return downTimeDetectedTemplate;
    }


    public Ping setDownTimeDetectedTemplate( String downTimeDetectedTemplate ) {
        this.downTimeDetectedTemplate = downTimeDetectedTemplate;

        return this;
    }


    public String getDownTimeEndedTemplate() {
        return downTimeEndedTemplate;
    }


    public Ping setDownTimeEndedTemplate( String downTimeEndedTemplate ) {
        this.downTimeEndedTemplate = downTimeEndedTemplate;

        return this;
    }


    public String getSlowDownDetectedTemplate() {
        return slowDownDetectedTemplate;
    }


    public Ping setSlowDownDetectedTemplate( String slowDownDetectedTemplate ) {
        this.slowDownDetectedTemplate = slowDownDetectedTemplate;

        return this;
    }


    public String getSlowDownEndedTemplate() {
        return slowDownEndedTemplate;
    }


    public Ping setSlowDownEndedTemplate( String slowDownEndedTemplate ) {
        this.slowDownEndedTemplate = slowDownEndedTemplate;

        return this;
    }


    public JsonArrayColumn< String > getAlertTechnicalEmails() {
        return alertTechnicalEmails;
    }


    public Ping setAlertTechnicalEmails( List< Object > alertTechnicalEmails ) {
        JsonArrayColumn< String > jsonArrayColumn = new JsonArrayColumn<>();

        if ( alertTechnicalEmails != null ) {
            alertTechnicalEmails.forEach( object -> jsonArrayColumn.add( object.toString() ) );
        }

        this.alertTechnicalEmails = jsonArrayColumn;

        return this;
    }


    public JsonArrayColumn< String > getAlertTechnicalPhones() {
        return alertTechnicalPhones;
    }


    public Ping setAlertTechnicalPhones( List< Object > alertTechnicalPhones ) {
        JsonArrayColumn< String > jsonArrayColumn = new JsonArrayColumn<>();

        if ( alertTechnicalPhones != null ) {
            alertTechnicalPhones.forEach( object -> jsonArrayColumn.add( object.toString() ) );
        }

        this.alertTechnicalPhones = jsonArrayColumn;

        return this;
    }


    public JsonArrayColumn< String > getAlertUserEmails() {
        return alertUserEmails;
    }


    public Ping setAlertUserEmails( List< Object > alertUserEmails ) {
        JsonArrayColumn< String > jsonArrayColumn = new JsonArrayColumn<>();

        if ( alertUserEmails != null ) {
            alertUserEmails.forEach( object -> jsonArrayColumn.add( object.toString() ) );
        }

        this.alertUserEmails = jsonArrayColumn;

        return this;
    }
}
