package com.lingogame.game.webservices;
import com.lingogame.game.domain.Game;
import com.lingogame.game.domain.Turn;
import com.lingogame.game.domain.User;
import com.lingogame.game.persistence.GameService;
import com.lingogame.game.persistence.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/player")
public class UserResource {
    private GameService gameService;
    private UserService userService;

    public UserResource(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<User> createNew(@RequestBody User user) {
        Game game = gameService.createGame();
        user.setGame(game);
        User s = userService.createNew(user);
        if (game != null){
            return new ResponseEntity<>(s, HttpStatus.CREATED);
        }else{
            return null;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Optional<User>> login(@RequestBody User user) {
        Optional<User> u = userService.findById(user);
        if (u.get().getName() != null){
            return new ResponseEntity<>(u, HttpStatus.CREATED);
        }else{
            return null;
        }
    }
}
