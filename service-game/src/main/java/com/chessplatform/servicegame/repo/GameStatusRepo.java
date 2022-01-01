package com.chessplatform.servicegame.repo;

import com.chessplatform.servicegame.repo.model.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameStatusRepo extends JpaRepository<GameStatus, Long> {
    GameStatus findByName(String name);
}
