package com.ask0n.springboot.config;

import com.ask0n.springboot.profile.DevProfile;
import com.ask0n.springboot.profile.ProductionProfile;
import com.ask0n.springboot.profile.SystemProfile;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties(prefix = "com.ask0n")
@Configuration
public class ApplicationConfig {
    @Bean
    @ConditionalOnProperty(prefix = "com.ask0n.profile", name = "dev", havingValue = "true")
    public SystemProfile devProfile() {
        return new DevProfile();
    }

    @Bean
    @ConditionalOnProperty(prefix = "com.ask0n.profile", name = "dev", havingValue = "false")
    public SystemProfile prodProfile() {
        return new ProductionProfile();
    }
}
