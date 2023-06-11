package com.free.ping.repository;

import com.free.ping.entity.Incident;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class IncidentRepositoryImpl extends AbstractRepository< Incident > implements IncidentRepository {
    public IncidentRepositoryImpl( EntityManager entityManager, JpaRepository< Incident, Long > jpaRepository ) {
        super( entityManager, jpaRepository );
    }


    @Override
    protected Class< Incident > getClassType() {
        return Incident.class;
    }
}
