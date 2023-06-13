package com.free.ping.repository;

import com.free.ping.entity.Incident;
import com.free.ping.entity.Ping;
import com.free.ping.repository.jpa.IncidentJpa;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidentRepositoryImpl extends AbstractRepository< Incident > implements IncidentRepository {
    protected final IncidentJpa incidentJpa;


    public IncidentRepositoryImpl( EntityManager entityManager, IncidentJpa incidentJpa ) {
        super( entityManager, incidentJpa );
        this.incidentJpa = incidentJpa;
    }


    @Override
    public List< Incident > findAllByPing( Ping ping ) {
        return incidentJpa.findAllByPing( ping );
    }


    @Override
    public List< Incident > findAllOpened() {
        return incidentJpa.findAllByAtIsNull();
    }


    @Override
    protected Class< Incident > getClassType() {
        return Incident.class;
    }
}
