package com.lingogame.game.webservices;

import com.lingogame.game.domain.Game;
import com.lingogame.game.domain.Turn;
import com.lingogame.game.persistence.TurnService;
import com.lingogame.game.persistence.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/round")
public class TurnResource {
    private final TurnService turnService;
    private final WordService wordService;
    private String randomword;

    @Autowired
    public TurnResource(TurnService turnService, WordService wordService) {
        this.turnService = turnService;
        this.wordService = wordService;
    }

    @GetMapping("/word/{length}")
    public Turn getRandomWord(@PathVariable int length) throws FileNotFoundException {//geeft een random woord terug aan het get request met alleen de eerste letter zichtbaar
        String numberOfLines = "";
        randomword = wordService.returnRandomWord(length);
        String firstLetter = wordService.ReturnFirstChar(numberOfLines);
        Turn t = new Turn();
        t.setHint(firstLetter);
        return t;
    }

    @Transactional
    @PostMapping("{gameid}")
    public ResponseEntity<Turn> guessWord(@RequestBody Turn turn, @PathVariable int gameid) throws FileNotFoundException {
        if(randomword == null){
            randomword = turnService.findRandomWordOnGameId(gameid);
        }
        Game g = new Game();
        g.setId(gameid);
        turn.setGame(g);
        turn.setRandomWord(randomword);
        Turn t = turnService.correctGuessedChars(turn, randomword); //get new turn object.
        randomword = t.getRandomWord();
        return new ResponseEntity<>(t, HttpStatus.CREATED);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<Turn>> getRounds(@PathVariable int id) {
        System.out.println(id);
        List<Turn> turns = turnService.findRounds(id);
        return new ResponseEntity<>(turns, HttpStatus.OK);
    }
}
