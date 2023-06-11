package com.free.ping.repository.jpa;

import com.free.ping.entity.Ping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PingJpa extends JpaRepository< Ping, Long > {
}
