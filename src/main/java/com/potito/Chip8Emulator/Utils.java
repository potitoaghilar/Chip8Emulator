package com.potito.Chip8Emulator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {

    public static byte[] readFile(String path) {
        try {

            return Files.readAllBytes(Paths.get(path));

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
