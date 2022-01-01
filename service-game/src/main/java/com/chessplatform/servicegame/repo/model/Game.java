package com.chessplatform.servicegame.repo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private String whitePlayer;
    private String blackPlayer;

    @ManyToOne(fetch = FetchType.EAGER)
    private GameStatus status;

    private String PGN;

}
