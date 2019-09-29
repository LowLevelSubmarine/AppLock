package com.lowlevelsubmarine.applock;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class AppLock {

    private static final MessageDigest DIGEST = createDigest();
    private static final Cipher CIPHER = createCipher();

    private final SecretKeySpec key;

    public AppLock(byte[] key) {
        this.key = new SecretKeySpec(key, "AES");
    }

    public static byte[] generateKey(long salt) {
        byte[] bytes = new byte[16];
        new Random(salt).nextBytes(bytes);
        return bytes;
    }

    public String sign(String unsigned) {
        try {
            return Hex.encode(this.encrypt(Hex.decode(unsigned), this.key));
        } catch (InvalidKeyException e) {
            throw new InternalError(e.getMessage());
        }
    }

    public static String hash(String string) {
        return hash(string.getBytes());
    }

    public static String hash(byte[] bytes) {
        return Hex.encode(DIGEST.digest(bytes));
    }

    private byte[] encrypt(byte[] bytes, SecretKeySpec key) throws InvalidKeyException {
        try {
            CIPHER.init(Cipher.ENCRYPT_MODE, key);
            return CIPHER.doFinal(bytes);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new InternalError(e.getMessage());
        }
    }

    private static Cipher createCipher() {
        try {
            return Cipher.getInstance("AES/ECB/NoPadding");
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            throw new InternalError("Failed to create cipher");
        }
    }

    private static MessageDigest createDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

}
