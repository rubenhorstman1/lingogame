package com.lingogame.game.persistence;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class TextDeserializer implements FileDeserializerInterface {

    @Override
    public List<String> deserialize(String file) throws FileNotFoundException {
        List<String> words = new ArrayList<>();
        File myObj = new File(file);
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            words.add(data);
        }
        myReader.close();
        return words;
    }
}
