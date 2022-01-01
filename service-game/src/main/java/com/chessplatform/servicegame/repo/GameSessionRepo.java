package com.chessplatform.servicegame.repo;

import com.chessplatform.servicegame.repo.model.Game;
import com.chessplatform.servicegame.repo.model.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameSessionRepo extends JpaRepository<GameSession, Long> {
    GameSession findByWhitePlayer(String username);
    GameSession findByBlackPlayer(String username);
}
