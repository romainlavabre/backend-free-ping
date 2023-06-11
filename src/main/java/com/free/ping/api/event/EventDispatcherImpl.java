package com.free.ping.api.event;

import com.free.ping.configuration.event.Event;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service
public class EventDispatcherImpl implements EventDispatcher {

    protected boolean isSorted = false;

    protected List< EventSubscriber > eventSubscribers;


    @Override
    public EventDispatcher trigger( final Event event, final Map< String, Object > params ) {
        if ( !isSorted ) {
            synchronized (this) {
                eventSubscribers = sortSubscribers( SubscriberCollector.INSTANCE.eventSubscribers );
            }
        }

        for ( final EventSubscriber eventSubscriber : eventSubscribers ) {
            if ( eventSubscriber.getEvents().contains( event ) ) {
                eventSubscriber.receiveEvent( event, params );
            }
        }

        return this;
    }


    protected List< EventSubscriber > sortSubscribers( final List< EventSubscriber > eventSubscribers ) {
        final List< EventSubscriber > result = new ArrayList<>();

        final Map< Integer, List< EventSubscriber > > temporarySort = new HashMap<>();

        int maxPriority = 0;

        for ( final EventSubscriber eventSubscriber : eventSubscribers ) {
            if ( eventSubscriber.getPriority() > maxPriority ) {
                maxPriority = eventSubscriber.getPriority();
            }

            if ( temporarySort.containsKey( eventSubscriber.getPriority() ) ) {
                temporarySort.get( eventSubscriber.getPriority() ).add( eventSubscriber );
                continue;
            }

            final List< EventSubscriber > listByPriority = new ArrayList<>();
            listByPriority.add( eventSubscriber );

            temporarySort.put( eventSubscriber.getPriority(), listByPriority );
        }

        for ( int i = 1; i <= maxPriority; i++ ) {
            if ( temporarySort.containsKey( i ) ) {
                result.addAll( temporarySort.get( i ) );
            }
        }

        if ( temporarySort.containsKey( 0 ) ) {
            result.addAll( temporarySort.get( 0 ) );
        }

        return result;
    }
}
