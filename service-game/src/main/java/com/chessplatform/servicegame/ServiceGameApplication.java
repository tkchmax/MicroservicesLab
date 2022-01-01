package com.chessplatform.servicegame;

import com.chessplatform.servicegame.repo.model.Game;
import com.chessplatform.servicegame.repo.model.GameSession;
import com.chessplatform.servicegame.repo.model.GameStatus;
import com.chessplatform.servicegame.service.GameService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServiceGameApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceGameApplication.class, args);
    }


    @Bean
    CommandLineRunner run(GameService gameService)
    {
        return args-> {
            gameService.saveStatus(new GameStatus(null, "1-0"));
            gameService.saveStatus(new GameStatus(null, "0-1"));
            gameService.saveStatus(new GameStatus(null, "0.5-0.5"));
            gameService.saveStatus(new GameStatus(null, "in_progress"));
            gameService.addGameSession("user1","user2");


        };
    }

}
