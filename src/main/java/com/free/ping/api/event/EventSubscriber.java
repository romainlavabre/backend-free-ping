package com.free.ping.api.event;


import com.free.ping.configuration.event.Event;

import java.util.List;
import java.util.Map;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 * {@see https://github.com/romainlavabre/spring-starter-event.git}
 */
public interface EventSubscriber {

    /**
     * @return List of events to which you are subscribed
     */
    List< Event > getEvents();


    /**
     * Will receive event target where is launched
     *
     * @param event
     * @param params
     */
    void receiveEvent( Event event, Map< String, Object > params )
            throws RuntimeException;


    /**
     * 0 implies that it is not a priority, so it will be called at the end,
     * 1 indicates a very high priority and more is a lower priority
     *
     * @return priority
     */
    int getPriority();
}
