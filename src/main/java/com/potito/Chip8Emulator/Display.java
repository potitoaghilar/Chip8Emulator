package com.potito.Chip8Emulator;

/**
 * Display is monochromatic
 * */
public class Display {

    private int width, height;
    private byte[] pixels;

    public Display(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new byte[width * height];
    }

    public byte getPixelColor(int x, int y) {
        return pixels[y * width + x];
    }

    public void setPixelColor(int x, int y, byte value) {
        pixels[y * width + x] = value;
    }

    public byte[] getPixels() {
        return pixels;
    }
}
