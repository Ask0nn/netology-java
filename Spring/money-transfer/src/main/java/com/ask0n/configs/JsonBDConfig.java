package com.ask0n.configs;

import com.ask0n.JsonBD;
import com.ask0n.models.Card;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
public class JsonBDConfig {
    @Bean
    public JsonBD<Card> getCardContext() {
        return new JsonBD<>(Path.of("card.json"), Card[].class, true);
    }
}
