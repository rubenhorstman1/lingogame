package com.lingogame.game.persistence;

import com.lingogame.game.domain.Game;
import com.lingogame.game.domain.Turn;
import com.lingogame.game.repo.GameRepo;
import com.lingogame.game.repo.TurnRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class TurnServiceTest {
    //test
    @Autowired
    private TurnService turnService;

    @MockBean
    private TurnRepo turnRepo;

    @Test
    @DisplayName("plays a round in game")
    void addRound() {
        Game game = new Game(
                1, 0, "unfinished"
        );

        Turn turn  = new Turn();
        turn.setGame(game);
        turn.setRandomWord("hahah");
        turn.setFeedback("absent, absent, correct, correct, correct");
        turn.setHint("__aha");
        turn.setGuessedWord("blaha");
        turn.setMistakes(1);

        Mockito.when(turnRepo.save(any(Turn.class))).thenReturn(turn);
        Turn t = turnService.addRound(turn);

        assertEquals(t, turn);
    }

    @Test
    @DisplayName("gets the randomword for the game")
    void findRandomWordOnGameId() {
        Game game = new Game(
                0, "unfinished"
        );
        game.setId(1);

        Turn turn  = new Turn();
        turn.setGame(game);
        turn.setRandomWord("hahah");
        turn.setFeedback("absent, absent,correct, correct, correct");
        turn.setHint("__aha");
        turn.setGuessedWord("blahahaha");
        turn.setMistakes(1);
        turn.setId(1);

        Mockito.when(turnRepo.findRandomWordOnGameId(any(int.class))).thenReturn("hahah");

        String woord = turnService.findRandomWordOnGameId(1);

        assertEquals(woord, turn.getRandomWord());
    }

    @Test
    @DisplayName("finds the round for the game")
    void findRounds() {
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
        turn.setId(1);

        List<Turn> turns = new ArrayList<>();
        turns.add(turn);

        Mockito.when(turnRepo.findAllTurnsById(any(int.class))).thenReturn(turns);

        List<Turn> t = turnService.findRounds(turn.getId());

        assertEquals(t, turns);
    }

    @ParameterizedTest
    @DisplayName("checks the correctchars")
    @MethodSource("correctGuessedChar")
    void correctGuessedChars(Turn input, Turn expected) throws FileNotFoundException {
        Turn t = turnService.correctGuessedChars(input, input.getRandomWord());

        if(input.getRandomWord().equals(input.getGuessedWord())){
            t.setHint(expected.getHint());
            t.setRandomWord(expected.getRandomWord());
        }

        assertEquals(t.getHint(), expected.getHint());
        assertEquals(t.getRandomWord(), expected.getRandomWord());
        assertEquals(t.getGame().getScore(), expected.getGame().getScore());
        assertEquals(t.getFeedback(), expected.getFeedback());
        assertEquals(t.getGame().getId(),expected.getGame().getId());
        assertEquals(t.getGame().getState(), expected.getGame().getState());
        assertEquals(t.getGuessedWord(), expected.getGuessedWord());
        assertEquals(t.getId(), expected.getId());
        assertEquals(t.getMistakes(), expected.getMistakes());
    }

    static Stream<Arguments> correctGuessedChar(){
        Turn expected;
        Turn input;
        Game game = new Game(
                1, 100, "in progress"
        );
        Game game3 = new Game(
                1, 0, "in progress"
        );
        Game game2 = new Game(
                1, 0, "finished"
        );
//        Game game4 = new Game();
//        game4.setId(1);
        return Stream.of(
                Arguments.of(input = new Turn(
                                "hahah","hahahd",5, game3,
                                "Het gerade woord is niet de juiste lengte",""
                        ),
                        expected = new Turn(
                                "hahah","hahahd",5, game2,
                                "Het gerade woord is niet de juiste lengte","")),
                Arguments.of(input = new Turn(
                        "hahah","blahaha",4, game3,
                        "Het gerade woord is niet de juiste lengte","h____"
                ), expected = new Turn(
                        "hahah","blahaha",5, game3,
                        "Het gerade woord is niet de juiste lengte","h____"
                )),
                Arguments.of(input = new Turn(
                                "hahah","hahah",0, game3,
                                "",""
                        ),
                        expected = new Turn(
                                "random",null,0, game,
                                null, "l_____ 6 tekens lang")),
                Arguments.of(input = new Turn(
                                "hahah","hhahz",0, game3,
                                "","h_a__"
                        ),
                        expected = new Turn(
                                "hahah","hhahz",1, game3,
                                "correct, present, present, present, absent, ", "h_a__")));
//                Arguments.of(input = new Turn(
//                                "hahah","hhahz",0, game4,
//                                "","h_a__"
//                        ),
//                        expected = new Turn(
//                                "hahah","hhahz",1, game3,
//                                "correct, present, present, present, absent, ", "h_a__")));
    }


    @ParameterizedTest
    @DisplayName("checked the guessed chars")
    @MethodSource("checkGuessedChar")
    void checkGuessedChars(Turn input, Turn expected) {
        String uitkomst = turnService.checkGuessedChars(input);

        assertEquals(uitkomst, expected.getHint());
    }

    static Stream<Arguments> checkGuessedChar(){
        Turn expected;
        Turn input;
        Game game3 = new Game(
                1, 0, "unfinished"
        );

        return Stream.of(Arguments.of(input = new Turn(
                "hahah","blahaha",4, game3,
                "absent, absent,correct, correct, correct",""
        ), expected = new Turn(
                "hahah","blahaha",5, game3,
                "Het gerade woord is niet de juiste lengte",""
        )),Arguments.of(input = new Turn(
                "hahah","hahaa",4, game3,
                "correct, correct,correct, absent, absent","hah__"
        ), expected = new Turn(
                "hahah","hahaa",5, game3,
                "correct, correct, correct, correct, absent","haha_"
        )));
    }


    @ParameterizedTest
    @DisplayName("checks the char on present or absent")
    @MethodSource("checkChar")
    void checkCharPresentOrAbsent(char currentChar, String correctChar, String randomword, String expected) {

        String uitkomst = turnService.checkCharPresentOrAbsent(currentChar,correctChar, randomword);

        assertEquals(expected, uitkomst);
    }

    static Stream<Arguments> checkChar(){
        return Stream.of(Arguments.of('c',"c","cancer","c_"),
                Arguments.of('a',"a","cancer", "a_"),
                Arguments.of('n',"n","cancer","n_"),
                Arguments.of('r',"","cancer","_"),
                Arguments.of('z',"","cancer","_"));
    }

}
