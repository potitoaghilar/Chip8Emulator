package com.potito.Chip8Emulator;

import javax.rmi.CORBA.Util;
import java.util.*;

public class Chip8 {

    // Main objects
    private Memory memory;
    private Registers registers;
    private Stack stack;
    private Input input;
    private Display display;

    // Pointers
    private short pc; // Program counter
    private short I; // Memory address
    //private short sp; // Stack pointer

    // Hardware vars
    private static int memorySize = 4096; // 4K memory
    private static short memoryEntryPoint = 0x200; // Entry point at address 512
    private static int displayWidth = 64;
    private static int displayHeight = 32;

    // Running vars
    private Thread runThread;
    private boolean isRunning = false;

    // OnRender event handler
    private ArrayList<OnRenderListener> onRenderListeners = new ArrayList<>();

    // Font (Sprite)
    private static short memorySpriteEntryPoint = 0x0; // Sprites are loaded between 0x0 and 0x200
    private static int spriteSize = 4 * 5; // 20 byte per sprite

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
        //sp = 0;

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

        short opcode = getOpcode();
        printOpcode(opcode);

        switch (opcode & 0xF000) {

            case 0x2000: { // Calls subroutine at NNN.
                short nnn = (short)(opcode & 0x0FFF);
                stack.push(pc);
                pc = nnn;
                pc -= 2; // To compensate +2 jump
                System.out.println("Calls subroutine at " + Utils.hexValue(nnn, true));
                break;
            }

            case 0x6000: { // Sets VX to NN.
                byte x = (byte)((opcode & 0x0F00) >> 8);
                byte nn = (byte)(opcode & 0x00FF);
                registers.setRegister(x, nn);
                System.out.println("Sets V" + Utils.hexValue(x, false) + " to " + Utils.hexValue(nn, true));
                break;
            }

            case 0x7000: { // Adds NN to VX. (Carry flag is not changed)
                byte x = (byte)((opcode & 0x0F00) >> 8);
                byte nn = (byte)(opcode & 0x00FF);
                byte sumNoCarry = (byte)(registers.getRegister(x) + nn);
                registers.setRegister(x, sumNoCarry);
                System.out.println("Adds " + Utils.hexValue(nn, true) + " to V" + Utils.hexValue(x, false) + ". Result: " + Utils.hexValue(sumNoCarry, true));
                break;
            }

            case 0xA000: { // Sets I to the address NNN.
                short nnn = (short)(opcode & 0x0FFF);
                I = nnn;
                System.out.println("Sets I to " + Utils.hexValue(nnn, true));
                break;
            }

            case 0xD000: { //Draws a sprite at coordinate (VX, VY) that has a width of 8 pixels and a height of N pixels. Each row of 8 pixels is read as bit-coded starting from memory location I; I value doesn’t change after the execution of this instruction. As described above, VF is set to 1 if any screen pixels are flipped from set to unset when the sprite is drawn, and to 0 if that doesn’t happen
                needRender = true;
                NotImplementedYet();
                break;
            }

            case 0xF000: {

                switch (opcode & 0x00FF) {

                    case 0x0029: { // Sets I to the location of the sprite for the character in VX. Characters 0-F (in hexadecimal) are represented by a 4x5 font.
                        I = memory.get(memorySpriteEntryPoint + spriteSize * ((opcode & 0x0F00) >> 8));
                        System.out.println("Sets I to " + Utils.hexValue(I, true));
                        break;
                    }

                    case 0x0033: { // Take the decimal representation of VX, place the hundreds digit in memory at location in I, the tens digit at location I+1, and the ones digit at location I+2
                        byte vx = registers.getRegister((byte)((opcode & 0x0F00) >> 8));
                        byte h = (byte)Utils.getNumberAtPosition(vx, 2);
                        byte d = (byte)Utils.getNumberAtPosition(vx, 1);
                        byte u = (byte)Utils.getNumberAtPosition(vx, 0);
                        memory.set(I, h);
                        memory.set(I + 1, d);
                        memory.set(I + 2, u);
                        System.out.print("Sets [" + Utils.hexValue(I, true) + "] to " + Utils.hexValue(h, true));
                        System.out.print(" - Sets [" + Utils.hexValue((short)(I + 1), true) + "] to " + Utils.hexValue(d, true));
                        System.out.println(" - Sets [" + Utils.hexValue((short)(I + 2), true) + "] to " + Utils.hexValue(u, true));
                        break;
                    }

                    case 0x0065: { // Fills V0 to VX (including VX) with values from memory starting at address I. I is increased by 1 for each value written.
                        byte regIndex = (byte)((opcode & 0x0F00) >> 8);

                        int registersFilled = 0;
                        for(int i = 0; i < regIndex; i++) {
                            registers.setRegister(regIndex, memory.get(I + i));
                            registersFilled++;
                        }

                        System.out.println("Filling registers from V0 to V" + Utils.hexValue(regIndex, false) + " with values from [" + Utils.hexValue(I, true) + "] to [" + Utils.hexValue((short)(I + registersFilled), true) + "]");
                        break;
                    }

                    default: {
                        NotImplementedYet();
                        System.exit(1);
                    }

                }

                break;
            }

            default: {
                NotImplementedYet();
                System.exit(1);
            }
        }

        pc += 2;

        if(needRender) {
            render();
        }
    }

    private short getOpcode() {
        return (short) ((Utils.convertToUnsigned(memory.get(pc)) << 8) | Utils.convertToUnsigned(memory.get(pc + 1)));
    }

    private void printOpcode(short opcode) {
        System.out.print(Integer.toHexString(Utils.convertToUnsigned(opcode)) + ": ");
    }

    private void NotImplementedYet() {
        System.out.println("\u001B[31mOpcode not implemented yet\u001B[0m");
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
