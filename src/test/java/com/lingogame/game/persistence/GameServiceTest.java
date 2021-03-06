package com.lingogame.game.persistence;

import com.lingogame.game.domain.Game;
import com.lingogame.game.repo.GameRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
class GameServiceTest {

    @Autowired
    private GameService gameService;

    @MockBean
    private GameRepo gameRepo;

    @Test
    @DisplayName("find game on id")
    void findGameId() {
        Game game = new Game(
                0,"unfinished"
        );
        game.setId(1);

        Mockito.when(gameRepo.findGameId(any(int.class))).thenReturn(game);
        Game g2 = gameService.findGameId(1);

        assertEquals(g2, game);
    }

    @Test
    @DisplayName("add a game")
    void addGame(){
        Game game = new Game(
                1, 0, "unfinished"
        );

        Mockito.when(gameRepo.save(any(Game.class))).thenReturn(game);

        Game g = gameService.addGame(game);

        assertEquals(g, game);
    }

    @Test
    @DisplayName("creates a game")
    void createGame() {
        Game game = new Game(
                0,"unfinished"
        );

        Mockito.when(gameRepo.save(any(Game.class))).thenReturn(game);
        Game savedGame = gameService.createGame();

        assertEquals(game, savedGame);
    }

    @Test
    @DisplayName("update a game")
    void updateGame() {
        Game game = new Game(
                100,"finished"
        );

        Mockito.when(gameRepo.save(any(Game.class))).thenReturn(game);

        Game g2 = gameService.updateGame(game);

        assertEquals(g2.getScore(), game.getScore());
        assertEquals(g2.getState(), game.getState());
    }
}

