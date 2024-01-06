package com.free.ping.module.ping.engine;

import com.free.ping.configuration.event.Event;
import com.free.ping.entity.Incident;
import com.free.ping.entity.Ping;
import com.free.ping.module.mail.SendMail;
import com.free.ping.repository.IncidentRepository;
import com.free.ping.repository.PingRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.romainlavabre.event.EventSubscriber;
import org.romainlavabre.rest.RequestBuilder;
import org.romainlavabre.rest.Response;
import org.romainlavabre.rest.Rest;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
@Scope( proxyMode = ScopedProxyMode.TARGET_CLASS )
public class PingCheckpoint implements EventSubscriber {

    private Map< Integer, List< Ping > > CACHE            = null;
    private Map< Ping, Incident >        CURRENT_INCIDENT = null;

    protected final PingRepository       pingRepository;
    protected final IncidentRepository   incidentRepository;
    protected final EntityManagerFactory entityManagerFactory;
    protected final SendMail             sendMail;


    public PingCheckpoint(
            PingRepository pingRepository,
            IncidentRepository incidentRepository,
            EntityManagerFactory entityManagerFactory,
            SendMail sendMail ) {
        this.pingRepository       = pingRepository;
        this.incidentRepository   = incidentRepository;
        this.entityManagerFactory = entityManagerFactory;
        this.sendMail             = sendMail;
        initCache();
        initCurrentIncident();
    }


    @Async
    @Scheduled( fixedRate = 1000, initialDelay = 3000 )
    public void controller() throws InterruptedException, ExecutionException {
        if ( CACHE == null || CURRENT_INCIDENT == null ) {
            return;
        }

        ZonedDateTime now = ZonedDateTime.now( ZoneOffset.UTC );

        List< Ping > pings = CACHE.get( now.getSecond() );

        if ( pings == null ) {
            return;
        }

        Map< Ping, Future< Incident > > futures = new HashMap<>();

        for ( Ping ping : pings ) {
            futures.put( ping, ping( ping ) );
        }

        while ( true ) {
            boolean hasNoCompleted = false;

            for ( Map.Entry< Ping, Future< Incident > > entry : futures.entrySet() ) {
                if ( !entry.getValue().isCancelled() ) {
                    hasNoCompleted = true;
                    break;
                }
            }

            if ( hasNoCompleted ) {
                break;
            }

            Thread.sleep( 100 );
        }

        EntityManager entityManager = null;

        for ( Map.Entry< Ping, Future< Incident > > entry : futures.entrySet() ) {
            if ( entry.getValue().get() != null ) {
                Ping     ping            = entry.getKey();
                Incident incident        = entry.getValue().get();
                Incident currentIncident = CURRENT_INCIDENT.get( ping );

                for ( Map.Entry< Ping, Incident > entry1 : CURRENT_INCIDENT.entrySet() ) {
                    if ( entry1.getKey().getId() == ping.getId() ) {
                        currentIncident = entry1.getValue();
                    }
                }

                if ( currentIncident != null && currentIncident.getType() == incident.getType() ) {
                    continue;
                }

                if ( entityManager == null ) {
                    entityManager = entityManagerFactory.createEntityManager();
                    entityManager.getTransaction().begin();
                }

                if ( currentIncident != null ) {
                    currentIncident = entityManager.find( Incident.class, currentIncident.getId() );

                    currentIncident.setAt( ZonedDateTime.now( ZoneOffset.UTC ) );

                    entityManager.persist( currentIncident );
                } else {
                    sendMail.send( incident );
                }

                entityManager.persist( incident );

                CURRENT_INCIDENT.put( entry.getKey(), incident );
            } else {
                Incident currentIncident = null;

                for ( Map.Entry< Ping, Incident > entry1 : CURRENT_INCIDENT.entrySet() ) {
                    if ( entry1.getKey().getId() == entry.getKey().getId() ) {
                        currentIncident = entry1.getValue();
                    }
                }

                if ( currentIncident != null ) {
                    if ( entityManager == null ) {
                        entityManager = entityManagerFactory.createEntityManager();
                        entityManager.getTransaction().begin();
                    }

                    Incident incident = entityManager.find( Incident.class, currentIncident.getId() );

                    incident.setAt( ZonedDateTime.now( ZoneOffset.UTC ) );

                    entityManager.persist( incident );

                    CURRENT_INCIDENT.remove( entry.getKey() );

                    sendMail.send( incident );
                }
            }
        }

        if ( entityManager != null ) {
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }


    @Async
    public Future< Incident > ping( Ping ping ) {
        long start = System.currentTimeMillis();
        Response response =
                Rest.builder()
                        .init( RequestBuilder.GET, ping.getPingUrl() )
                        .buildAndSend();

        long elapsedTime = System.currentTimeMillis() - start;

        if ( !response.isSuccess() ) {
            Incident incident = new Incident();

            incident.setOf( ZonedDateTime.now( ZoneOffset.UTC ) )
                    .setType( Incident.TYPE_DOWN_TIME )
                    .setPing( ping );

            return new AsyncResult<>( incident );
        }

        if ( elapsedTime / 1000 >= ping.getSlowDownSeconds() ) {
            Incident incident = new Incident();

            incident.setOf( ZonedDateTime.now( ZoneOffset.UTC ) )
                    .setType( Incident.TYPE_SLOW_DOWN )
                    .setPing( ping );

            return new AsyncResult<>( incident );
        }


        return new AsyncResult<>( null );
    }


    @Override
    public List< Enum > getEvents() {
        return List.of(
                Event.TRANSACTION_SUCCESS
        );
    }


    @Override
    public void receiveEvent( Enum event, Map< String, Object > params ) throws RuntimeException {
        initCache();
    }


    protected void initCache() {
        synchronized ( this ) {
            Map< Integer, List< Ping > > result = new HashMap<>();

            List< Ping > pings = pingRepository.findAll();

            int i = 0;

            for ( Ping ping : pings ) {
                int seconds = i;

                while ( seconds <= 59 ) {
                    if ( !result.containsKey( seconds ) ) {
                        result.put( seconds, new ArrayList<>() );
                    }

                    result.get( seconds ).add( ping );

                    seconds += ping.getInterval();
                }

                if ( i == 3 ) {
                    i = 0;
                } else {
                    i++;
                }
            }

            CACHE = result;
        }
    }


    protected void initCurrentIncident() {
        List< Incident > incidents = incidentRepository.findAllOpened();

        CURRENT_INCIDENT = new HashMap<>();

        for ( Incident incident : incidents ) {
            CURRENT_INCIDENT.put( incident.getPing(), incident );
        }
    }


    @Override
    public int getPriority() {
        return 0;
    }
}
