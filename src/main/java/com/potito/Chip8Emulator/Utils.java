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

    public static int convertToUnsigned(byte val) {
        return val & 0xFF;
    }

    public static int convertToUnsigned(short val) {
        return val & 0xFFFF;
    }

    public static String hexValue(byte val, boolean prefix) {
        return (prefix ? "0x" : "") + Integer.toHexString(convertToUnsigned(val)).toUpperCase();
    }

    public static String hexValue(short val, boolean prefix) {
        return (prefix ? "0x" : "") + Integer.toHexString(convertToUnsigned(val)).toUpperCase();
    }

    public static int getNumberAtPosition(byte val, int position) {
        String decimalVX = new StringBuilder(Integer.toString(convertToUnsigned(val))).reverse().toString();

        if(position > decimalVX.length() - 1) {
            return 0;
        } else {
            return Integer.parseInt(decimalVX.substring(position, position + 1));
        }

    }

}
