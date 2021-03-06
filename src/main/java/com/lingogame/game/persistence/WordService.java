package com.lingogame.game.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class WordService {
    private TextDeserializer textDeserializer;
    public String randomWord;

    @Autowired
    public WordService(TextDeserializer textDeserializer) {
        this.textDeserializer = textDeserializer;
    }

    public String returnHint(String numberOfLines) {
        for (int i = 0; i < randomWord.length() - 1; i++) {
            numberOfLines += "_";
        }
        return randomWord.substring(0, 1) + numberOfLines + " " + randomWord.length() + " tekens lang";
    }

    public String returnRandomWord(int size) throws FileNotFoundException {
        if(size > 7 || size < 5){
            size = 5;
        }
        List<String> words = textDeserializer.deserialize("src/main/resources/woorden-opentaal.txt");
        List<String> checkedWords = loopWords(words, size);
        int rnd = new Random().nextInt(checkedWords.size());
        randomWord = checkedWords.get(rnd);
        return randomWord;
    }

    public List<String> loopWords(List<String> content, int size) {
        List<String> checkedWords = new ArrayList<>();
        for (int i = 0; i < content.size(); i++) {
            if(content.get(i).length() == size) {
                String data = content.get(i);
                checkedWords.add(data);
            }
        }
        return checkedWords;
    }
}
