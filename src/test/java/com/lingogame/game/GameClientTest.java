package com.lingogame.game;

import com.lingogame.game.domain.Word;
import com.lingogame.game.persistence.TextDeserializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class GameClientTest {

  @MockBean
  RestTemplate restTemplate;

  @Autowired
  TextDeserializer textDeserializer;

  @Test
  @DisplayName("import words - happy")
  void ImportWords() throws FileNotFoundException {

    Word word1 = new Word("tester");
    Word word2 = new Word("tested");
    Word word3 = new Word("testje");
    Word[] words = {word1, word2, word3};

    Mockito.when(restTemplate.getForEntity(any(String.class), any())).thenReturn(new ResponseEntity<>(words, HttpStatus.OK));

    List<String> lingowords = textDeserializer.deserialize("src/main/resources/woorden-opentaal.txt");
    assertNotNull(lingowords);
    assertEquals(lingowords.get(0), words[0]);
  }
}
