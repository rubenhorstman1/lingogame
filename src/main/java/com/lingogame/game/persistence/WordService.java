package com.lingogame.game.persistence;

import com.lingogame.game.domain.Word;
import com.lingogame.game.repo.WordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Service
public class WordService {
    private final WordRepo wordRepo;
    private TextDeserializer textDeserializer;
    private String randomWord;

    @Autowired
    public WordService(WordRepo wordRepo, TextDeserializer textDeserializer) {
        this.wordRepo = wordRepo;
        this.textDeserializer = textDeserializer;
    }

    public Word addWord(Word word){
        return wordRepo.save(word);
    }

    public List<Word> findWords(){
        return wordRepo.findAll();
    }

    public String ReturnFirstChar(String numberOfLines) {
        for (int i = 0; i < randomWord.length() - 1; i++) {
            numberOfLines += "_";
        }
        String returnWaarde = randomWord.substring(0, 1) + numberOfLines + " " + randomWord.length() + " tekens lang";
        System.out.println(returnWaarde+" randomWord "+randomWord);
        return returnWaarde;
    }

    public String returnRandomWord(int size) throws FileNotFoundException {//geeft een random woord terug
        if(size > 7 || size < 5){
            size = 5;
        }
        List<String> lingowords = textDeserializer.deserialize("src/main/resources/woorden-opentaal.txt");
        List<String> checkedWords = loopTroughWords(lingowords, size);
        int rnd = new Random().nextInt(checkedWords.size());
        randomWord = checkedWords.get(rnd);
        System.out.println(randomWord+" random ");
        return randomWord;
    }

    public List<String> loopTroughWords(List<String> content, int size) {//looped door de woorden heen en stopt ze in een array
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
