package com.lingogame.game.webservices;

import com.lingogame.game.domain.Game;
import com.lingogame.game.domain.Turn;
import com.lingogame.game.persistence.GameService;
import com.lingogame.game.persistence.TurnService;
import com.lingogame.game.webservices.TurnResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/game")
public class GameResource {
    private final GameService gs;
    private final TurnService ts;
    private final TurnResource tr;

    @Autowired
    public GameResource(GameService gs, TurnService ts, TurnResource tr) {
        this.gs = gs;
        this.ts = ts;
        this.tr = tr;
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
