package com.chessplatform.servicegame.repo;

import com.chessplatform.servicegame.repo.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepo extends JpaRepository<Game, Long> {
}
