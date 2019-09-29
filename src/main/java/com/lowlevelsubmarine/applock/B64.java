package com.lowlevelsubmarine.applock;

import java.util.Base64;

public class B64 {

    public static String encode(byte[] decoded) {
        return Base64.getEncoder().encodeToString(decoded);
    }

    public static byte[] decode(String encoded) {
        return Base64.getDecoder().decode(encoded);
    }

}
