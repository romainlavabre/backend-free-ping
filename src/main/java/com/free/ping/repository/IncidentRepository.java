package com.free.ping.repository;

import com.free.ping.entity.Incident;
import com.free.ping.entity.Ping;

import java.util.List;

public interface IncidentRepository extends DefaultRepository< Incident > {
    List< Incident > findAllByPing( Ping ping );


    List< Incident > findAllOpened();
}
