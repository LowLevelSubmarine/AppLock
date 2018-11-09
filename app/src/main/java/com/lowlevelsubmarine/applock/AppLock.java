package com.lowlevelsubmarine.applock;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AppLock {

    private static final Cipher CIPHER = createCipher();

    public static boolean valid(IdentifierBytes idBytes, String signed, SecretKeySpec key) throws InvalidKeyException  {
        try {
            Bytes signedBytes = new Bytes(signed);
            CIPHER.init(Cipher.DECRYPT_MODE, key);
            return Arrays.equals(CIPHER.doFinal(signedBytes.getBytes()), idBytes.getBytes());
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new InternalError("Impossible error");
        }
    }

    public String sign(IdentifierBytes idBytes, SecretKeySpec key) throws InvalidKeyException {
        try {
            CIPHER.init(Cipher.ENCRYPT_MODE, key);
            return BaseSixtyFour.encode(CIPHER.doFinal(idBytes.getBytes()));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new InternalError("Impossible error");
        }
    }

    public static Cipher createCipher() {
        try {
            return Cipher.getInstance("AES/ECB/NoPadding");
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            throw new InternalError("Failed to create cipher");
        }
    }

}
