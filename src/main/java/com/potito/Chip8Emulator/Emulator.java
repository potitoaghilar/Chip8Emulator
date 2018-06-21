package com.potito.Chip8Emulator;

public class Emulator {

    public static void main(String[] args) {

        String romPath = "./rom/pong.rom";

        MainWindow mainWindow = new MainWindow("Chip8 Emulator - " + romPath, 64, 32, 10);

        // On render listener
        OnRenderListener onRenderListener = mainWindow::render;

        Chip8 chip8 = new Chip8();
        chip8.addOnRenderListener(onRenderListener);
        chip8.loadROM(romPath);
        //chip8.run();

    }

}
