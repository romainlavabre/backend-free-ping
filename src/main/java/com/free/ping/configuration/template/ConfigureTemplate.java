package com.free.ping.configuration.template;

import com.free.ping.configuration.environment.Variable;
import org.romainlavabre.environment.Environment;
import org.romainlavabre.template.TemplateConfigurer;
import org.springframework.stereotype.Service;

@Service
public class ConfigureTemplate {
    protected final Environment environment;


    public ConfigureTemplate( Environment environment ) {
        this.environment = environment;
        configure();
    }


    private void configure() {
        TemplateConfigurer
                .init()
                .setBaseTemplatePath( environment.getEnv( Variable.BASE_TEMPLATE_PATH ) )
                .build();
    }
}
