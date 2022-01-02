package com.lingogame.game.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TextDeserializerTest {

    @Autowired
    private FileDeserializerInterface fileDeserializerInterface;

    @Autowired
    private TextDeserializer textDeserializer;

    @ParameterizedTest
    @DisplayName("strips the file and puts the words in a array")
    @MethodSource("deserializes")
    void deserialize(String file, int position, String expected) throws FileNotFoundException {
        List<String> files = fileDeserializerInterface.deserialize(file);

        System.out.println(files.get(position));

        assertEquals(files.get(position), expected);
    }

    static Stream<Arguments> deserializes(){
        return Stream.of(Arguments.of("src/test/resources/woorden-opentaal.txt",8000,"amoxicilline"),
                Arguments.of("src/test/resources/woorden-opentaal.txt",8001,"ampel"),
                Arguments.of("src/test/resources/woorden-opentaal.txt",8002,"amper"));
    }


}
