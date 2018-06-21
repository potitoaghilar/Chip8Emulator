package com.potito.Chip8Emulator;

public class Memory {

    private byte[] memory;

    public Memory(int memorySize) {
        memory = new byte[memorySize];
    }

    public byte get(int address) {
        return memory[address];
    }

    public void set(int address, byte value) {
        memory[address] = value;
    }
}
