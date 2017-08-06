package com.siddhu.capp.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtils {
    public static final String KEY_ONE = "MZygpewJsCpRrfOr";
    private static final String TAG = EncryptionUtils.class.getSimpleName();
    private EncryptionUtils() {

    }

    public static String encrypt(final String encryptionKey, final String plainText) {
        try {
            final int flags = Base64.NO_WRAP | Base64.URL_SAFE;
            Cipher cipher = getCipher(encryptionKey, Cipher.ENCRYPT_MODE);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

            return Base64.encodeToString(encryptedBytes, flags);

        } catch (Exception e) {

            Logger.log(TAG,e);

        }
        return plainText;
    }

    public static String decrypt(final String encryptionKey, final String encryptedText) {
        try {
            final int flags = Base64.NO_WRAP | Base64.URL_SAFE;
            Cipher cipher = getCipher(encryptionKey, Cipher.DECRYPT_MODE);
            byte[] plainBytes = cipher.doFinal(Base64.decode(encryptedText, flags));

            return new String(plainBytes);
        }catch (Exception e) {
            Logger.log(TAG,e);
        }

        return encryptedText;
    }

    private static Cipher getCipher(final String encryptionKey, final int cipherMode) throws UnsupportedEncodingException,
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException {
        final String encryptionAlgorithm = "AES";
        final SecretKeySpec spec = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), encryptionAlgorithm);
        Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
        cipher.init(cipherMode, spec);

        return cipher;
    }
}
