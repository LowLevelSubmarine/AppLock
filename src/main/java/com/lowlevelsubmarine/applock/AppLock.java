package com.lowlevelsubmarine.applock;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class AppLock {

    private final Cipher cipher = createCipher();
    private final SecretKeySpec key;

    public AppLock(byte[] key) {
        this.key = new SecretKeySpec(key, "AES");
    }

    public byte[] generateKey(long salt) {
        byte[] bytes = new byte[16];
        new Random(salt).nextBytes(bytes);
        return bytes;
    }

    public String sign(String unsigned) throws InvalidKeyException {
        return B64.encode(this.encrypt(B64.decode(unsigned), this.key));
    }

    private byte[] encrypt(byte[] bytes, SecretKeySpec key) throws InvalidKeyException {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(bytes);
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
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

}
