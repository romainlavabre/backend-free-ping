package com.free.ping.repository.jpa;

import com.free.ping.entity.Incident;
import com.free.ping.entity.Ping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentJpa extends JpaRepository< Incident, Long > {
    List< Incident > findAllByPing( Ping ping );


    List< Incident > findAllByAtIsNull();
}
