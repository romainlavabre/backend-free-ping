package com.free.ping;

import org.romainlavabre.environment.Environment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication( scanBasePackages = { "com.free.ping", "org.romainlavabre" } )
@EntityScan( { "com.free.ping", "org.romainlavabre" } )
@EnableJpaRepositories( { "com.free.ping", "org.romainlavabre" } )
@EnableScheduling
@EnableAsync
public class FreePingApplication {

    public static void main( final String[] args ) {
        SpringApplication.run( FreePingApplication.class, args );
    }


    @Bean
    public WebMvcConfigurer corsConfigurer( final Environment environment ) {


        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings( final CorsRegistry registry ) {

                final String pattern = "/**";
                final String origins = environment.getEnv( "request.allowed-origin" );

                registry.addMapping( pattern )
                        .allowedMethods( "GET", "POST", "PUT", "DELETE" )
                        .allowedOrigins( origins )
                        .exposedHeaders( "Location", "Authorization" );
            }
        };

    }
}
