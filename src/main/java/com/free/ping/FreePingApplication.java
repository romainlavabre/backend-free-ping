package com.free.ping;

import com.free.ping.api.environment.Environment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
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
