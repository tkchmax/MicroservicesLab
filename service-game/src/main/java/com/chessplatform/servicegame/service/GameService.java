package com.chessplatform.servicegame.service;


import com.chessplatform.servicegame.api.dto.UserDto;
import com.chessplatform.servicegame.repo.GameRepo;
import com.chessplatform.servicegame.repo.GameSessionRepo;
import com.chessplatform.servicegame.repo.model.Game;
import com.chessplatform.servicegame.repo.GameStatusRepo;
import com.chessplatform.servicegame.repo.model.GameSession;
import com.chessplatform.servicegame.repo.model.GameStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameService implements IGameService {
    private final GameRepo gameRepo;
    private final GameStatusRepo gameStatusRepo;
    private final GameSessionRepo gameSessionRepo;


    static boolean isUserExist(String username)
    {
        RestTemplate restTemplate = new RestTemplate();
        try
        {
            String usernameUrl = "http://192.168.189.50:30001/user/get_by_name?username="+username;
            ResponseEntity<UserDto> maybeUser = restTemplate.getForEntity(usernameUrl, UserDto.class);

        }catch (Exception ex)
        {
            log.error("Reported user {} does not exist!", username);
            return false;
        }
        return true;
    }

    @Override
    public void playGame(String username) throws Exception {
        if(!isUserExist(username)) {
            log.error("user {} does not exist!", username);
            throw new Exception("user '" + username + "' does not exist");
        }

        GameSession maybeGame = getGameSessionByUsername(username);
        if(maybeGame != null) {
            log.error("user {} is already in game!", username);
            throw new Exception("user '" + username + "' is already in game");
        }


        GameSession session = findSessionToJoin();
        if(session != null) {
            session.setBlackPlayer(username);
            log.info("PLAY GAME: joining to session id={}", session.getId());
            gameSessionRepo.save(session);
        }
        else
        {
            session = addGameSession(username, null);
            log.info("PLAY GAME: created new session id={}",session.getId());
        }
    }


    @Override
    public void resign(String username) throws Exception {
        GameSession session = getGameSessionByUsername(username);
        if(session == null)
            throw new Exception("user '"+username+"' is not playing");

        if(session.getBlackPlayer().equals(username))
            addGame(session, getStatus("1-0"));
        else
            addGame(session, getStatus("0-1"));

        gameSessionRepo.delete(session);
    }

    @Override
    public GameSession findSessionToJoin() throws EntityNotFoundException{
        return getGameSessionByUsername(null);
    }

    @Override
    public GameSession addGameSession(String whitePlayerUsername, String blackPlayerUsername) {
        GameSession session = new GameSession(null, whitePlayerUsername, blackPlayerUsername,"");
        gameSessionRepo.save(session);
        log.info("ADD GAME_SESSION: created new game session id={}",session.getId());
        return session;
    }

    @Override
    public GameSession getGameSessionById(long id) {
        return gameSessionRepo.getById(id);
    }

    @Override
    public GameSession getGameSessionByUsername(String username) throws EntityNotFoundException {
       GameSession game = gameSessionRepo.findByWhitePlayer(username);
       if(game == null)
       {
           game = gameSessionRepo.findByBlackPlayer(username);
           return game;
       }
        return game;
    }

    @Override
    public List<GameSession> getGameSessions() {
        return gameSessionRepo.findAll();
    }

    @Override
    public GameStatus getStatus(String name) {
        GameStatus status = gameStatusRepo.findByName(name);
        return status;
    }

    @Override
    public GameStatus saveStatus(GameStatus status) {
        gameStatusRepo.save(status);
        return status;
    }

    @Override
    public Game getGameById(long id) {
        return gameRepo.getById(id);
    }

    @Override
    public List<Game> getGames() {
        return gameRepo.findAll();
    }

    @Override
    public void addGame(GameSession gameSession, GameStatus status) {
        gameRepo.save(new Game(null, gameSession.getWhitePlayer(), gameSession.getBlackPlayer(), status, gameSession.getPGN()));
    }
}
