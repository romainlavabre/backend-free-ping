package com.free.ping.module.ping;

import com.free.ping.api.event.EventSubscriber;
import com.free.ping.api.rest.RequestBuilder;
import com.free.ping.api.rest.Response;
import com.free.ping.api.rest.Rest;
import com.free.ping.configuration.event.Event;
import com.free.ping.entity.Incident;
import com.free.ping.entity.Ping;
import com.free.ping.repository.PingRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Scope( proxyMode = ScopedProxyMode.TARGET_CLASS )
public class PingCheckpoint implements EventSubscriber {

    private Map< Integer, List< Ping > > CACHE = new HashMap<>();

    protected final PingRepository pingRepository;


    public PingCheckpoint( PingRepository pingRepository ) {
        this.pingRepository = pingRepository;
    }


    @Async
    @Scheduled( fixedRate = 1000 )
    public void controller() {
        System.out.println( "CONTROLLER" );
        ZonedDateTime now = ZonedDateTime.now( ZoneOffset.UTC );

        List< Ping > pings = CACHE.get( now.getSecond() );

        if ( pings == null ) {
            return;
        }

        for ( Ping ping : pings ) {
            ping( ping );
        }

    }


    @Async
    public void ping( Ping ping ) {
        System.out.println( "PING" );
        long start = System.nanoTime();
        Response response =
                Rest.builder()
                        .init( RequestBuilder.GET, ping.getPingUrl() )
                        .buildAndSend();

        long elapsedTime = System.nanoTime() - start;

        if ( !response.isSuccess() ) {
            Incident incident = new Incident();

            incident.setOf( ZonedDateTime.now( ZoneOffset.UTC ) )
                    .setType( Incident.TYPE_DOWN_TIME )
                    .setPing( ping );
        }

        if ( elapsedTime / 1000 > ping.getSlowDownSeconds() ) {
            Incident incident = new Incident();

            incident.setOf( ZonedDateTime.now( ZoneOffset.UTC ) )
                    .setType( Incident.TYPE_SLOW_DOWN )
                    .setPing( ping );
        }


    }


    @Override
    public List< Event > getEvents() {
        return List.of(
                Event.TRANSACTION_SUCCESS
        );
    }


    @Override
    public void receiveEvent( Event event, Map< String, Object > params ) throws RuntimeException {
        synchronized ( this ) {
            Map< Integer, List< Ping > > result = new HashMap<>();

            List< Ping > pings = pingRepository.findAll();

            for ( Ping ping : pings ) {
                int seconds = 0;

                while ( seconds <= 59 ) {
                    if ( !result.containsKey( seconds ) ) {
                        result.put( seconds, new ArrayList<>() );
                    }

                    result.get( seconds ).add( ping );

                    seconds += ping.getInterval();
                }
            }
        }
    }


    @Override
    public int getPriority() {
        return 0;
    }
}
