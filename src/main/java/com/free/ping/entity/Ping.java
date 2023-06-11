package com.free.ping.entity;

import com.free.ping.configuration.response.Message;
import com.free.ping.entity.converter.JsonArrayColumn;
import com.free.ping.entity.converter.JsonArrayColumnConverter;
import com.free.ping.exception.HttpUnprocessableEntityException;
import jakarta.persistence.*;

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

    private String versionField;

    @Convert( converter = JsonArrayColumnConverter.class )
    private final JsonArrayColumn< String > alertTechnicalEmails;

    @Convert( converter = JsonArrayColumnConverter.class )
    private final JsonArrayColumn< String > alertTechnicalPhones;

    @Convert( converter = JsonArrayColumnConverter.class )
    private final JsonArrayColumn< String > alertUserEmails;


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


    public String getVersionField() {
        return versionField;
    }


    public Ping setVersionField( String versionField ) {
        this.versionField = versionField;

        return this;
    }


    public JsonArrayColumn< String > getAlertTechnicalEmails() {
        return alertTechnicalEmails;
    }


    public Ping addAlertTechnicalEmail( String email ) {
        if ( !alertTechnicalEmails.contains( email ) ) {
            alertTechnicalEmails.add( email );
        }

        return this;
    }


    public Ping removeAlertTechnicalEmail( String email ) {
        alertTechnicalEmails.remove( email );

        return this;
    }


    public JsonArrayColumn< String > getAlertTechnicalPhones() {
        return alertTechnicalPhones;
    }


    public Ping addAlertTechnicalPhone( String phone ) {
        if ( !alertTechnicalPhones.contains( phone ) ) {
            alertTechnicalPhones.add( phone );
        }

        return this;
    }


    public Ping removeAlertTechnicalPhone( String phone ) {
        alertTechnicalPhones.remove( phone );

        return this;
    }


    public JsonArrayColumn< String > getAlertUserEmails() {
        return alertUserEmails;
    }


    public Ping addAlertUserEmail( String email ) {
        if ( !alertUserEmails.contains( email ) ) {
            alertUserEmails.add( email );
        }

        return this;
    }


    public Ping removeAlertUserEmail( String email ) {
        alertUserEmails.remove( email );

        return this;
    }
}
