package com.lingogame.game.persistence;

import com.lingogame.game.domain.Game;
import com.lingogame.game.domain.Turn;
import com.lingogame.game.repo.TurnRepo;
import org.checkerframework.checker.signature.qual.MethodDescriptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class TurnServiceTest {

    @Autowired
    private TurnService turnService;

    @MockBean
    private TurnRepo turnRepo;

    @Test
    void addRound() {

      Game game = new Game(
        1, 0, "unfinished"
      );

      Turn turn  = new Turn();
      turn.setGame(game);
      turn.setRandomWord("hahah");
      turn.setFeedback("absent, absent,correct, correct, correct");
      turn.setHint("__aha");
      turn.setGuessedWord("blaha");
      turn.setMistakes(1);

      Mockito.when(turnRepo.save(any(Turn.class))).thenReturn(turn);

      Turn t = turnService.addRound(turn);

      assertEquals(t, turn);
    }

    @Test
    void findRandomWordOnGameId() {

    }

    @Test
    void findRounds() {

    }

    @Test
    void correctGuessedChars() {

    }

    @Test
    void checkGuessedChars() {
      Game game = new Game(
        1, 0, "unfinished"
      );

      Turn turn  = new Turn();
      turn.setGame(game);
      turn.setRandomWord("hahah");
      turn.setFeedback("absent, absent,correct, correct, correct");
      turn.setHint("__aha");
      turn.setGuessedWord("blahahaha");
      turn.setMistakes(1);

      String uitkomst = turnService.checkGuessedChars(turn);
      turn.setGuessedWord("hahha");
      String uitkomst2 = turnService.checkGuessedChars(turn);

      assertEquals(uitkomst, "Het gerade woord is niet de juiste lengte");
      assertEquals(uitkomst2, "hah__");
    }

    @Test
    @MethodSource("CheckChar")
    void checkCharPresentOrAbsent(char currentChar, String CorrectChar, String randomword) {

        String uitkomst = turnService.checkCharPresentOrAbsent(currentChar,CorrectChar, randomword);

        System.out.println(uitkomst);
        assertEquals(uitkomst, "present");
    }

    static Stream<Arguments> checkChar(){
        return Stream.of(Arguments.of('c',"","cancer"),
          Arguments.of('a',"c","cancer"),
          Arguments.of('n',"ca","cancer"));
    }

    @Test
    @MethodSource("CheckCharOccurences")
    void checkCharOccurencesInRandomWord(char currentChar, String CorrectChar, String randomword) {
      int uitkomst = turnService.checkCharOccurencesInRandomWord(currentChar,CorrectChar, randomword);

      assertEquals(uitkomst, 1);
    }
  //oeps
    static Stream<Arguments> CheckCharOccurences(){
      return Stream.of(Arguments.of('c',"","cancer"),
        Arguments.of('a',"c","cancer"),
        Arguments.of('n',"ca","cancer"));
    }

}
