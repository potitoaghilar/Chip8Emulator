package com.potito.Chip8Emulator;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public class Chip8 {

    // Main objects
    private Memory memory;
    private Registers registers;
    private Stack stack;
    private Input input;
    private Display display;

    // Pointers
    private int pc; // Program counter
    private int I; // Memory address
    private int sp; // Stack pointer

    // Hardware vars
    private static int memorySize = 4096; // 4K memory
    private static int memoryEntryPoint = 0x200; // Entry point at address 512
    private static int displayWidth = 64;
    private static int displayHeight = 32;

    // Running vars
    private Thread runThread;
    private boolean isRunning = false;

    // OnRender event handler
    private ArrayList<OnRenderListener> onRenderListeners = new ArrayList<>();

    public Chip8() {

        // Create objects
        this.memory = new Memory(memorySize);
        this.registers = new Registers();
        this.stack = new Stack();
        this.input = new Input();
        this.display = new Display(displayWidth, displayHeight);

        // Init pointers
        pc = memoryEntryPoint;
        I = 0;
        sp = 0;

    }

    public void loadROM(String path) {
        byte[] romData = Utils.readFile(path);
        if(Utils.readFile(path) != null) {

            // Copy rom to memory
            for(int i = 0; i < romData.length; i++) {
                memory.set(memoryEntryPoint + i, romData[i]);
            }

        }
    }

    public void run() {
        isRunning = true;
        runThread = new Thread(() -> {
            try {

                while (isRunning) {
                    executeCycle();
                    Thread.sleep(16); // 60 FPS
                }

            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        });

        runThread.start();
    }

    public void stop() {
        isRunning = false;
    }

    private void executeCycle() {
        boolean needRender = false;

        short opcode = (short)((memory.get(pc) << 8) | memory.get(pc + 1));

        System.out.print(Integer.toHexString(opcode) + ": ");

        switch (opcode & 0xF000) {
            case 0x0000:

                break;
            default:
                System.err.println("Opcode not implemented yet");
                System.exit(1);
        }

        pc += 2;

        if(needRender) {
            render();
        }
    }

    private void render() {
        byte[] pixels = display.getPixels();
        for (OnRenderListener listener : onRenderListeners) {
            listener.OnRender(pixels);
        }
    }

    public void addOnRenderListener(OnRenderListener listener) {
        onRenderListeners.add(listener);
    }

}

interface OnRenderListener {
    void OnRender(byte[] pixels);
}
