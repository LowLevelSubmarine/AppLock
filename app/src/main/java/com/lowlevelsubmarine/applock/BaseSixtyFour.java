package com.lowlevelsubmarine.applock;

import android.util.Base64;

public class BaseSixtyFour {

    public static String encode(byte[] decoded) {
        return Base64.encodeToString(decoded, Base64.DEFAULT);
    }

    public static byte[] decode(String encoded) {
        return Base64.decode(encoded, Base64.DEFAULT);
    }

}
