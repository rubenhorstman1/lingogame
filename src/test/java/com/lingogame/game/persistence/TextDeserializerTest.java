package com.lingogame.game.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;
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