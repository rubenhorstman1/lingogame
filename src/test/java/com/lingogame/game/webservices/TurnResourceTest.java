package com.lingogame.game.webservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lingogame.game.domain.Game;
import com.lingogame.game.domain.Turn;

import com.lingogame.game.persistence.TurnService;
import com.lingogame.game.persistence.WordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
class TurnResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TurnService turnService;

    @MockBean
    private WordService wordService;


    @Test
    @DisplayName("gets a random word")
    void getRandomWord() throws Exception {
        String value ="h____";
        int length = 5;

        Mockito.when(wordService.returnHint("")).thenReturn(value);

        mockMvc.perform(get("/turn/word/" + length).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.hint").value(value));
    }

    @Test
    @DisplayName("tries to guess the word")
    void guessWord() throws Exception {
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

        Mockito.when(turnService.addRound(any(Turn.class))).thenReturn(turn);

        Mockito.when(turnService.findRandomWordOnGameId(any(int.class))).thenReturn(turn.getRandomWord());

        Mockito.when(turnService.correctGuessedChars(any(Turn.class), any(String.class))).thenReturn(turn);

        mockMvc.perform(post("/turn/"+ game.getId()).contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(turn)))
                        .andExpect(status().isCreated());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    @DisplayName("gets the rounds of the game")
    void getRounds() throws Exception {
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

        List<Turn> turns = new ArrayList<>();
        turns.add(turn);

        Mockito.when(turnService.findRounds(turn.getId())).thenReturn(turns);

        mockMvc.perform(get("/turn/all/" + turn.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(turn.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].randomWord").value(turn.getRandomWord()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].guessedWord").value(turn.getGuessedWord()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mistakes").value(turn.getMistakes()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].feedback").value(turn.getFeedback()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].hint").value(turn.getHint()));
    }
}
