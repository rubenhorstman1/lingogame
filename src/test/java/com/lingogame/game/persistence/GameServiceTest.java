package com.lingogame.game.persistence;

import com.lingogame.game.domain.Game;
import com.lingogame.game.repo.GameRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
class GameServiceTest {

  @Autowired
  private GameService gameService;

  @MockBean
  private GameRepo gameRepo;

  @Test
  void findGameId() {
    Game game = new Game(
      0,"unfinished"
    );
    game.setId(1);

    Mockito.when(gameRepo.findById(any(int.class))).thenReturn(Optional.of(game));

    Game g2 = gameService.findGameId(1);
    assertEquals(g2.getId(), game.getId());

  }

  @Test
  void findGameScoreId() {
    Game game = new Game(
      0,"unfinished"
    );
    game.setId(1);

    Mockito.when(gameRepo.findGameScoreId(any(int.class))).thenReturn(game);

    Game g2 = gameService.findGameScoreId(1);
    assertEquals(g2.getId(), game.getId());
// yes
  }

  @Test
  void createGame() {
    Game game = new Game(
      0,"unfinished"
    );

    Mockito.when(gameRepo.save(any(Game.class))).thenReturn(game);
    Game savedGame = gameService.createGame();

    assertEquals(game, savedGame);
  }

  @Test
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
