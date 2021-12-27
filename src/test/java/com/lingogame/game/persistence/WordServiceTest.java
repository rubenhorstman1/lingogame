package com.lingogame.game.persistence;

import com.lingogame.game.repo.WordRepo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WordServiceTest {

    @Autowired
    private WordService wordService;

    @MockBean
    private WordRepo wordRepo;

    @Autowired
    private TextDeserializer textDeserializer;

    @ParameterizedTest
    @MethodSource("returnFirst")
    void returnFirstChar(String value, String expected) {
        wordService.randomWord = value;

        String woord = wordService.ReturnFirstChar("");

        assertEquals(woord, expected);
    }

    static Stream<Arguments> returnFirst(){
        return Stream.of(Arguments.of("toets","t____ 5 tekens lang"),
                Arguments.of("denken","d_____ 6 tekens lang"),
                Arguments.of("drankje","d______ 7 tekens lang"));
    }




    @ParameterizedTest
    @MethodSource("returnRandomWords")
    void returnRandomWord(int length, String expected) throws FileNotFoundException {
        String woord= wordService.returnRandomWord(length);

        assertEquals(woord.length(), expected.length());
    }

    static Stream<Arguments> returnRandomWords(){
        return Stream.of(Arguments.of(5,"toets"),
                Arguments.of(6,"denken"),
                Arguments.of(7,"drankje"),
                Arguments.of(8,"drank"),
                Arguments.of(4,"drank"));
    }



    @ParameterizedTest
    @MethodSource("loop")
    void loopTroughWords(String value, int size, String expected) throws FileNotFoundException {
        List<String> words = new ArrayList<>();
        words.add(value);

        List<String> d= wordService.loopTroughWords(words, size);
        assertEquals(d.get(0), expected);
    }


    static Stream<Arguments> loop(){
        return Stream.of(Arguments.of("toets",5,"toets"),
                Arguments.of("denken",6,"denken"),
                Arguments.of("drankje",7,"drankje"));
    }

}