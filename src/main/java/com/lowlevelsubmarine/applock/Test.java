package com.lowlevelsubmarine.applock;

public class Test {

    private static final byte[] key = AppLock.generateKey(-239757428);

    public static void main(String[] args) {
        AppLock appLock = new AppLock(key);
        String hash = AppLock.hash("TEST");
        System.out.println("Hash: " + hash);
        System.out.println("Signed hash: " + appLock.sign(hash));
    }

}
