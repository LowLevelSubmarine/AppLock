package com.lowlevelsubmarine.applock;

public class IdentifierBytes extends Bytes {

    public IdentifierBytes(byte[] raw) throws IllegalArgumentException {
        super(raw);
        if (raw.length != 256) {
            throw new IllegalArgumentException("Array must be 265 bytes long");
        }
    }

    public IdentifierBytes(String byteString) throws IllegalArgumentException {
        super(byteString);
        if (byteString.length() != 265) {
            throw new IllegalArgumentException("String must be 265 chars long");
        }
    }
}
