package com.lingogame.game.webservices;

import com.lingogame.game.domain.Game;
import com.lingogame.game.persistence.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameResource {
    private final GameService gs;

    @Autowired
    public GameResource(GameService gs) {
        this.gs = gs;
    }

    @PostMapping("/add")
    public ResponseEntity<Game> saveGame(@RequestBody Game game) {
        Game gm = gs.addGame(game);
        return new ResponseEntity<>(gm, HttpStatus.CREATED);
    }

    @GetMapping("/{gameid}")
    public ResponseEntity<Game> getGameId(@PathVariable int gameid) {
        Game g = gs.findGameId(gameid);
        return new ResponseEntity<>(g, HttpStatus.OK);
    }
}
