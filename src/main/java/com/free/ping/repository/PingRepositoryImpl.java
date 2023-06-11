package com.free.ping.repository;

import com.free.ping.entity.Ping;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PingRepositoryImpl extends AbstractRepository< Ping > implements PingRepository {

    public PingRepositoryImpl( EntityManager entityManager, JpaRepository< Ping, Long > jpaRepository ) {
        super( entityManager, jpaRepository );
    }


    @Override
    protected Class< Ping > getClassType() {
        return Ping.class;
    }
}
