package com.lingogame.game.persistence;

import com.lingogame.game.domain.Game;
import com.lingogame.game.domain.User;
import com.lingogame.game.repo.UserRepo;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @MockBean
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @Test
    void createNew() {
        Game game = new Game(
                50, "in progress"
        );

        User user = new User();
        user.setGame(game);
        user.setName("Ruben");

        User user2 = new User(
                game,"Ruben", 1
        );

        Mockito.when(userRepo.save(any(User.class))).thenReturn(user2);
        User u = userService.createNew(user);

        assertEquals(user2, u);
    }

    @Test
    void findById() {
        Game game = new Game(
                50, "in progress"
        );

        User user = new User(
                game,"Ruben"
        );

        Mockito.when(userRepo.findById(any(int.class))).thenReturn(Optional.of(user));

        Optional<User> i = userService.findById(user);

        System.out.println(i);
        assertEquals(i, Optional.of(user));
    }
}