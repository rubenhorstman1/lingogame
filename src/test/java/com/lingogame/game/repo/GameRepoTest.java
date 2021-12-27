package com.lingogame.game.repo;

import com.lingogame.game.domain.Game;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameRepoTest {

    @Autowired
    private GameRepo gameRepo;

    @Test
    void findGameId() {
        Game game = new Game(
                50, "in progress"
        );

        Game g2 = gameRepo.save(game);
        Game g = gameRepo.findGameId(g2.getId());

        assertEquals(g.getState(), g2.getState());
        assertEquals(g.getScore(), g2.getScore());
        assertEquals(g.getId(), g2.getId());
    }


}