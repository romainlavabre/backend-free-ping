package com.free.ping.repository.jpa;

import com.free.ping.entity.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentJpa extends JpaRepository< Incident, Long > {
}
