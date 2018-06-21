package com.potito.Chip8Emulator;

public class Registers {

    private byte V0, V1, V2, V3, V4, V5, V6, V7, V8, V9, VA, VB, VC, VD, VE, VF;

    public Registers() {
        V0 = 0x0;
        V1 = 0x0;
        V2 = 0x0;
        V3 = 0x0;
        V4 = 0x0;
        V5 = 0x0;
        V6 = 0x0;
        V7 = 0x0;
        V8 = 0x0;
        V9 = 0x0;
        VA = 0x0;
        VB = 0x0;
        VC = 0x0;
        VD = 0x0;
        VE = 0x0;
        VF = 0x0;
    }

    public void setRegister(byte register, byte value) {
        switch (register) {
            case 0x0: { setV0(value); }
            case 0x1: { setV1(value); }
            case 0x2: { setV2(value); }
            case 0x3: { setV3(value); }
            case 0x4: { setV4(value); }
            case 0x5: { setV5(value); }
            case 0x6: { setV6(value); }
            case 0x7: { setV7(value); }
            case 0x8: { setV8(value); }
            case 0x9: { setV9(value); }
            case 0xA: { setVA(value); }
            case 0xB: { setVB(value); }
            case 0xC: { setVC(value); }
            case 0xD: { setVD(value); }
            case 0xE: { setVE(value); }
            case 0xF: { setVF(value); }
        }
    }

    public byte getRegister(byte register) {
        switch (register) {
            case 0x0: { return getV0(); }
            case 0x1: { return getV1(); }
            case 0x2: { return getV2(); }
            case 0x3: { return getV3(); }
            case 0x4: { return getV4(); }
            case 0x5: { return getV5(); }
            case 0x6: { return getV6(); }
            case 0x7: { return getV7(); }
            case 0x8: { return getV8(); }
            case 0x9: { return getV9(); }
            case 0xA: { return getVA(); }
            case 0xB: { return getVB(); }
            case 0xC: { return getVC(); }
            case 0xD: { return getVD(); }
            case 0xE: { return getVE(); }
            case 0xF: { return getVF(); }
            default:
                return 0x0;
        }
    }

    public byte getV0() {
        return V0;
    }

    public void setV0(byte v0) {
        V0 = v0;
    }

    public byte getV1() {
        return V1;
    }

    public void setV1(byte v1) {
        V1 = v1;
    }

    public byte getV2() {
        return V2;
    }

    public void setV2(byte v2) {
        V2 = v2;
    }

    public byte getV3() {
        return V3;
    }

    public void setV3(byte v3) {
        V3 = v3;
    }

    public byte getV4() {
        return V4;
    }

    public void setV4(byte v4) {
        V4 = v4;
    }

    public byte getV5() {
        return V5;
    }

    public void setV5(byte v5) {
        V5 = v5;
    }

    public byte getV6() {
        return V6;
    }

    public void setV6(byte v6) {
        V6 = v6;
    }

    public byte getV7() {
        return V7;
    }

    public void setV7(byte v7) {
        V7 = v7;
    }

    public byte getV8() {
        return V8;
    }

    public void setV8(byte v8) {
        V8 = v8;
    }

    public byte getV9() {
        return V9;
    }

    public void setV9(byte v9) {
        V9 = v9;
    }

    public byte getVA() {
        return VA;
    }

    public void setVA(byte VA) {
        this.VA = VA;
    }

    public byte getVB() {
        return VB;
    }

    public void setVB(byte VB) {
        this.VB = VB;
    }

    public byte getVC() {
        return VC;
    }

    public void setVC(byte VC) {
        this.VC = VC;
    }

    public byte getVD() {
        return VD;
    }

    public void setVD(byte VD) {
        this.VD = VD;
    }

    public byte getVE() {
        return VE;
    }

    public void setVE(byte VE) {
        this.VE = VE;
    }

    public byte getVF() {
        return VF;
    }

    public void setVF(byte VF) {
        this.VF = VF;
    }
}
