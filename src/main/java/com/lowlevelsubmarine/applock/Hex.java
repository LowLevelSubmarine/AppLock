package com.lowlevelsubmarine.applock;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

public class Hex {

    public static String encode(byte[] decoded) {
        return HexBin.encode(decoded).toLowerCase();
    }

    public static byte[] decode(String encoded) {
        return HexBin.decode(encoded);
    }

}
