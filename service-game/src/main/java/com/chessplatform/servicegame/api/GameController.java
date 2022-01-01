package com.chessplatform.servicegame.api;

import com.chessplatform.servicegame.repo.model.Game;
import com.chessplatform.servicegame.repo.model.GameSession;
import com.chessplatform.servicegame.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;

    @GetMapping("/play")
    public ResponseEntity join(@RequestParam String username)
    {
        try
        {
            gameService.playGame(username);
        }catch(Exception ex)
        {
            return ResponseEntity.status(409).body(ex.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}/resign")
    public ResponseEntity resign(@PathVariable String username)
    {
        try
        {
            gameService.resign(username);
        }catch (Exception ex){
            return ResponseEntity.status(409).body(ex.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping ("get_all_sessions")
    public ResponseEntity getAllSessions()
    {
        List<GameSession> list = gameService.getGameSessions();
        return ResponseEntity.ok(list);
    }

    @GetMapping ("get_all_games")
    public ResponseEntity getAllGames()
    {
        List<Game> list = gameService.getGames();
        return ResponseEntity.ok(list);
    }

}
