package com.exceed.app.config;

import com.exceed.app.repository.TranslateRepository;
import com.exceed.app.repository.WordRepository;
import com.exceed.app.web.websocket.game.WordTranslateGame;
import java.util.function.Function;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
public class GameConfiguration {

    @Bean
    public Function<String, WordTranslateGame> gameFactory() {
        return name -> wordTranslateGame(); // or this::thing
    }

    @Bean
    @Scope("prototype")
    public WordTranslateGame wordTranslateGame() {
        return new WordTranslateGame();
    }
}
