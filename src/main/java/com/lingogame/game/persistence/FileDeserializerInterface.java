package com.lingogame.game.persistence;

import java.io.FileNotFoundException;
import java.util.List;

public interface FileDeserializerInterface {
    List<String> deserialize(String file) throws FileNotFoundException;
}
