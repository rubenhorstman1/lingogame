package com.lingogame.game.webservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lingogame.game.domain.Game;
import com.lingogame.game.persistence.GameService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GameResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Test
    void saveGame() throws Exception {
        Game game = new Game();

        game.setState("unfinished");
        game.setId(1);
        game.setScore(0);

        Mockito.when(gameService.addGame(game)).thenReturn(game);

        mockMvc.perform(post("/game/add").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(game)))
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
    void getGameId() throws Exception {
        Game game = new Game();
        game.setState("unfinished");
        game.setId(1);

        Mockito.when(gameService.findGameId(game.getId())).thenReturn(game);

        mockMvc.perform(get("/game/" + game.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(game.getId()))
                .andExpect(jsonPath("$.state").value(game.getState()));
    }

}