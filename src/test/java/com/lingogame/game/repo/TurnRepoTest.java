package com.lingogame.game.repo;

import com.lingogame.game.domain.Game;
import com.lingogame.game.domain.Turn;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TurnRepoTest {

    @Autowired
    private TurnRepo turnRepo;
    @Autowired
    private GameRepo gameRepo;

    private Game game;
    private Turn turn;

    @Test
    @DisplayName("finds all turns by game")
    void findAllTurnsById() {
        List<Turn> ts = new ArrayList<>();

        Game game = new Game(
                50, "in progress"
        );

        gameRepo.save(game);

        Turn turn = new Turn(
             "klaar", "kraag", 1, game, "correct, absent, correct, correct, absent", "k_aa_"
        );

        Turn t = turnRepo.save(turn);
        System.out.println(t.getId());
        ts.add(t);

        List<Turn> turns3 = turnRepo.findAllTurnsById(3);
        System.out.println(turns3);

        assertEquals(turns3.get(0).getHint(), ts.get(0).getHint());
        assertEquals(turns3.get(0).getId(), ts.get(0).getId());
        assertEquals(turns3.get(0).getRandomWord(), ts.get(0).getRandomWord());
        assertEquals(turns3.get(0).getMistakes(), ts.get(0).getMistakes());
        assertEquals(turns3.get(0).getGame().getScore(), ts.get(0).getGame().getScore());
        assertEquals(turns3.get(0).getGame().getId(), ts.get(0).getGame().getId());
        assertEquals(turns3.get(0).getGame().getState(), ts.get(0).getGame().getState());

    }

    @Test
    @DisplayName("finds the randomword from the game")
    void findRandomWordOnGameId() {

        Game game = new Game(
                  50, "in progress"
        );
        Game g = gameRepo.save(game);

        Turn turn = new Turn(
                 "klaar", "kraag", 1, g, "correct, absent, correct, correct, absent", "k_aa_"
        );
        turnRepo.save(turn);

        String randomword = turnRepo.findRandomWordOnGameId(g.getId());

        assertEquals(turn.getRandomWord(), randomword);
    }

}
