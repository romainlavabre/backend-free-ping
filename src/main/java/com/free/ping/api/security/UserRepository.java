package com.free.ping.api.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Repository
public interface UserRepository extends JpaRepository< User, Long > {

    User findByUsername( String username );
}
