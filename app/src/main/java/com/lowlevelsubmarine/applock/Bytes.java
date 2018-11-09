package com.lowlevelsubmarine.applock;

public class Bytes {

    private final byte[] bytes;

    public Bytes(byte[] raw) {
        this.bytes = raw;
    }

    public Bytes(String byteString) {
        this.bytes = BaseSixtyFour.decode(byteString);
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public String getString() {
        return BaseSixtyFour.encode(this.bytes);
    }

}
