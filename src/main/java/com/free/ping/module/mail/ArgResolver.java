package com.free.ping.module.mail;

import com.free.ping.api.environment.Environment;
import com.free.ping.api.template.TemplateBuilder;
import com.free.ping.configuration.environment.Variable;
import com.free.ping.entity.Incident;
import com.free.ping.entity.Ping;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ArgResolver {

    private static final String PING_TITLE             = "ping_title";
    private static final String PING_PING_URL          = "ping_ping_url";
    private static final String PING_SLOW_DOWN_SECONDS = "ping_slow_down_seconds";
    private static final String PING_INTERVAL          = "ping_interval";
    private static final String INCIDENT_OF            = "incident_of";
    private static final String INCIDENT_AT            = "incident_at";

    protected final TemplateBuilder templateBuilder;
    protected final Environment     environment;


    public ArgResolver( TemplateBuilder templateBuilder, Environment environment ) {
        this.templateBuilder = templateBuilder;
        this.environment     = environment;
    }


    public String resolveTemplate( String template, Incident incident ) {
        Ping ping = incident.getPing();

        String filename = UUID.randomUUID().toString();
        File   file     = new File( environment.getEnv( Variable.BASE_TEMPLATE_PATH ) + filename + ".vm" );

        try {
            file.createNewFile();
            Files.writeString( file.toPath(), template );
        } catch ( IOException e ) {
            throw new RuntimeException( e );
        }

        Map< String, Object > params = new HashMap<>();
        params.put( PING_TITLE, ping.getTitle() );
        params.put( PING_PING_URL, ping.getPingUrl() );
        params.put( PING_SLOW_DOWN_SECONDS, ping.getSlowDownSeconds() );
        params.put( PING_INTERVAL, ping.getInterval() );
        params.put( INCIDENT_OF, incident.getOf() );
        params.put( INCIDENT_AT, incident.getAt() );

        return templateBuilder.build( filename, params );
    }


    public String resolveSubject( Incident incident ) {
        String template = incident.getType() == Incident.TYPE_DOWN_TIME
                ? incident.getPing().getDownTimeUserSubject()
                : incident.getPing().getSlowDownUserSubject();

        Ping ping = incident.getPing();

        String filename = UUID.randomUUID().toString();
        File   file     = new File( environment.getEnv( Variable.BASE_TEMPLATE_PATH ) + filename + ".vm" );

        try {
            file.createNewFile();

            Files.writeString( file.toPath(), template );
        } catch ( IOException e ) {
            throw new RuntimeException( e );
        }

        Map< String, Object > params = new HashMap<>();
        params.put( PING_TITLE, ping.getTitle() );
        params.put( PING_PING_URL, ping.getPingUrl() );
        params.put( PING_SLOW_DOWN_SECONDS, ping.getSlowDownSeconds() );
        params.put( PING_INTERVAL, ping.getInterval() );
        params.put( INCIDENT_OF, incident.getOf() );
        params.put( INCIDENT_AT, incident.getAt() );

        return templateBuilder.build( filename, params );
    }


}
