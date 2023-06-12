package com.free.ping.configuration.startup;

import com.free.ping.api.environment.Environment;
import com.free.ping.api.security.PasswordEncoder;
import com.free.ping.api.security.User;
import com.free.ping.api.security.UserRepository;
import com.free.ping.configuration.environment.Variable;
import com.free.ping.configuration.security.Role;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service
public class DefaultAdmin {

    protected final Logger          logger = LoggerFactory.getLogger( getClass() );
    protected final UserRepository  userRepository;
    protected final Environment     environment;
    protected final EntityManager   entityManager;
    protected final PasswordEncoder passwordEncoder;


    public DefaultAdmin(
            UserRepository userRepository,
            Environment environment,
            EntityManager entityManager,
            PasswordEncoder passwordEncoder ) {
        this.userRepository  = userRepository;
        this.environment     = environment;
        this.entityManager   = entityManager;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    @EventListener( ApplicationReadyEvent.class )
    public void createDefaultAdmin() {
        if ( userRepository.findAll().isEmpty() ) {
            User user = new User();
            user.setUsername( environment.getEnv( Variable.DEFAULT_ADMIN_USERNAME ) );
            user.setPassword( passwordEncoder.encode( environment.getEnv( Variable.DEFAULT_ADMIN_PASSWORD ) ) );
            user.addRole( Role.ADMIN );
            
            entityManager.persist( user );
            entityManager.flush();

            logger.info( "Admin " + user.getUsername() + " was created" );
        }
    }
}
