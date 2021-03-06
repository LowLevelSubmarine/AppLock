package com.lowlevelsubmarine.applock;

public class Hex {

    private static final char[] HEX_CODE = "0123456789abcdef".toCharArray();

    public static String encode(byte[] data) {
        StringBuilder r = new StringBuilder(data.length*2);
        for ( byte b : data) {
            r.append(HEX_CODE[(b >> 4) & 0xF]);
            r.append(HEX_CODE[(b & 0xF)]);
        }
        return r.toString();
    }

    public static byte[] decode(String s) {
        final int len = s.length();
        // "111" is not a valid hex encoding.
        if( len%2 != 0 ) {
            throw new IllegalArgumentException("hexBinary needs to be even-length: " + s);
        }
        byte[] out = new byte[len/2];
        for( int i=0; i<len; i+=2 ) {
            int h = hexToBin(s.charAt(i  ));
            int l = hexToBin(s.charAt(i+1));
            if( h==-1 || l==-1 ) {
                throw new IllegalArgumentException("contains illegal character for hexBinary: " + s);
            }
            out[i/2] = (byte)(h*16+l);
        }
        return out;
    }

    private static int hexToBin(char ch) {
        if('0'<=ch && ch<='9') return ch-'0';
        if('A'<=ch && ch<='F') return ch-'A'+10;
        if('a'<=ch && ch<='f') return ch-'a'+10;
        return -1;
    }

}
