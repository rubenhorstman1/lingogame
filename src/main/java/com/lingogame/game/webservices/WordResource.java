package com.lingogame.game.webservices;

import com.lingogame.game.domain.Word;
import com.lingogame.game.persistence.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/word")
public class WordResource {
    private final WordService ws;

    @Autowired
    public WordResource(WordService ws) {
        this.ws = ws;
    }

    @PostMapping("/add")
    public ResponseEntity<Word> saveRound(@RequestBody Word word) {
        System.out.println(word.getId());
        Word w = ws.addWord(word);
        return new ResponseEntity<>(w, HttpStatus.CREATED);
    }
}
