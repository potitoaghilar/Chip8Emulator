package com.potito.Chip8Emulator;

public class UByte {

    private byte value;

    public UByte(int value) {
        this.value = (byte)value;
    }

    public short getValue() {
        return (short)(value & 0xFF);
    }

    public short setValue() {
        return (short)(value & 0xFF);
    }

}
