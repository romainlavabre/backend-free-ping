package com.free.ping.configuration.security;

import com.free.ping.api.security.AuthenticationFilter;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( securedEnabled = true, prePostEnabled = true )
public class SecurityConfig {

    private static final String[]           WHITE_ENDPOINT = {
            "/auth",
            "/api/documentation/**",
            "/v2/api-docs",
            "/v3/api-docs"
    };
    protected            UserDetailsService userDetailsService;


    public SecurityConfig(
            @Qualifier( "userDetailsService" ) final UserDetailsService userDetailsService ) {
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public SecurityFilterChain filterChain( final HttpSecurity http ) throws Exception {

        http
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS )
                .and()
                .anonymous()
                .and()
                .authorizeHttpRequests()
                .dispatcherTypeMatchers( DispatcherType.ERROR ).permitAll()
                .requestMatchers( HttpMethod.OPTIONS, "/**" ).permitAll()
                .requestMatchers( SecurityConfig.WHITE_ENDPOINT ).permitAll()
                .requestMatchers( "/**" ).hasRole( new SecurityRole( Role.ADMIN ).toString() )
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint( new HttpStatusEntryPoint( HttpStatus.UNAUTHORIZED ) );

        http
                .addFilterBefore( this.authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class );

        return http.build();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService( userDetailsService );
        authProvider.setPasswordEncoder( passwordEncoder() );

        return authProvider;
    }


    @Bean
    public AuthenticationFilter authenticationTokenFilterBean() {
        return new AuthenticationFilter();
    }


    @Bean
    public AuthenticationManager authenticationManager( AuthenticationConfiguration authConfiguration ) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    private class SecurityRole {
        private final String ROLE;


        public SecurityRole( String role ) {
            ROLE = role;
        }


        @Override
        public String toString() {
            return ROLE.replace( "ROLE_", "" );
        }
    }
}
