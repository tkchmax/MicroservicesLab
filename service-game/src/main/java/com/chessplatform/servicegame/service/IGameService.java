package com.chessplatform.servicegame.service;


import com.chessplatform.servicegame.repo.model.Game;
import com.chessplatform.servicegame.repo.model.GameSession;
import com.chessplatform.servicegame.repo.model.GameStatus;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface IGameService {
    GameSession getGameSessionById(long id);
    GameSession getGameSessionByUsername(String username);
    List<GameSession> getGameSessions();
    GameSession addGameSession(String whitePlayerUsername, String blackPlayerUsername);
    GameSession findSessionToJoin() throws EntityNotFoundException;

    GameStatus getStatus(String name);
    GameStatus saveStatus(GameStatus status);


    Game getGameById(long id);
    List<Game> getGames();
    void addGame(GameSession gameSession, GameStatus status);

    void playGame(String username) throws Exception;
    void resign(String username) throws Exception;
}
