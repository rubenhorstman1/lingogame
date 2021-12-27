package com.lingogame.game.webservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lingogame.game.domain.Game;
import com.lingogame.game.domain.User;
import com.lingogame.game.persistence.GameService;
import com.lingogame.game.persistence.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
class UserResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @MockBean
    private UserService userService;

    @Test
    void createNew() throws Exception {
        Game game = new Game();

        game.setState("unfinished");
        game.setId(1);
        game.setScore(0);

        Mockito.when(gameService.createGame()).thenReturn(game);

        User user = new User();
        user.setName("Ruben");
        user.setGame(game);

        Mockito.when(userService.createNew(user)).thenReturn(user);

        mockMvc.perform(post("/player").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    void login() throws Exception {
        Game game = new Game();

        game.setState("unfinished");
        game.setId(1);
        game.setScore(0);

        User user = new User(
                game, "ruben",1
        );

        Mockito.when(userService.findById(any(User.class))).thenReturn(Optional.of(user));

        mockMvc.perform(get("/player/"+user.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}