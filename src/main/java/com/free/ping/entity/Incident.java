package com.free.ping.entity;

import com.free.ping.configuration.response.Message;
import com.free.ping.exception.HttpUnprocessableEntityException;
import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

@Entity
public class Incident {

    public static final byte TYPE_DOWN_TIME = 0;
    public static final byte TYPE_SLOW_DOWN = 1;

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;

    @Column( nullable = false )
    private ZonedDateTime of;

    private ZonedDateTime at;

    private byte type;

    @ManyToOne
    @JoinColumn( nullable = false, name = "ping_id" )
    private Ping ping;


    public long getId() {
        return id;
    }


    public ZonedDateTime getOf() {
        return of;
    }


    public Incident setOf( String of ) {
        if ( of == null ) {
            throw new HttpUnprocessableEntityException( Message.INCIDENT_OF_REQUIRED );
        }

        try {
            this.of = ZonedDateTime.parse( of );
        } catch ( DateTimeParseException e ) {
            throw new HttpUnprocessableEntityException( Message.INCIDENT_OF_INVALID_FORMAT );
        }

        return this;
    }


    public ZonedDateTime getAt() {
        return at;
    }


    public Incident setAt( String at ) {

        try {

            this.at = ZonedDateTime.parse( at );
        } catch ( DateTimeParseException e ) {
            throw new HttpUnprocessableEntityException( Message.INCIDENT_AT_INVALID_FORMAT );
        }

        return this;
    }


    public byte getType() {
        return type;
    }


    public Incident setType( Byte type ) {
        if ( type == null ) {
            throw new HttpUnprocessableEntityException( Message.INCIDENT_TYPE_REQUIRED );
        }

        if ( type != TYPE_DOWN_TIME
                && type != TYPE_SLOW_DOWN ) {
            throw new HttpUnprocessableEntityException( Message.INCIDENT_TYPE_INVALID );
        }

        this.type = type;

        return this;
    }


    public Ping getPing() {
        return ping;
    }


    public Incident setPing( Ping ping ) {
        this.ping = ping;

        return this;
    }
}
