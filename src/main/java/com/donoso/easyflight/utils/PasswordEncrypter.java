package com.donoso.easyflight.utils;


import lombok.NoArgsConstructor;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


@NoArgsConstructor
public class PasswordEncrypter {
    private static final String IV = "0123456789123456";
    private static final String SECRET_KEY = "EasyFlight";
   /* public static void main(String[] args) {
        String encriptar = Utiles.encriptarAMD5("12345");
        System.out.println(encriptar);

        System.out.println(Utiles.desencriptarMD5(encriptar));

    }*/

    public String encrypt(final String dataToEncrypt, final String initialVector, final String secretKey) {

        String encryptedData = null;

        try {
            // Initialize the cipher
            final Cipher cipher = initCipher(Cipher.ENCRYPT_MODE, initialVector, secretKey);
            // Encrypt the data
            final byte[] encryptedByteArray = cipher.doFinal(dataToEncrypt.getBytes());
            // Encode using Base64
            encryptedData = Base64.getEncoder().encodeToString(encryptedByteArray);

        } catch (Exception e) {
            System.err.println("Problem encrypting the data");
            e.printStackTrace();
        }

        return encryptedData;

    }

    public String decrypt(final String encryptedData, final String initialVector, final String secretKey) {

        String decryptedData = null;

        try {
            // Initialize the cipher
            final Cipher cipher = initCipher(Cipher.DECRYPT_MODE, initialVector, secretKey);
            // Decode using Base64
            final byte[] encryptedByteArray = Base64.getDecoder().decode(encryptedData);
            // Decrypt the data
            final byte[] decryptedByteArray = cipher.doFinal(encryptedByteArray);
            decryptedData = new String(decryptedByteArray, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.err.println("Problem decrypting the data");
            e.printStackTrace();
        }

        return decryptedData;

    }


    private Cipher initCipher(final int mode, final String initialVectorString, final String secretKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {

        final SecretKeySpec skeySpec = new SecretKeySpec(md5(secretKey).getBytes(), "AES");
        final IvParameterSpec initialVector = new IvParameterSpec(initialVectorString.getBytes());
        final Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
        cipher.init(mode, skeySpec, initialVector);
        return cipher;
    }

    private static String md5(final String input) throws NoSuchAlgorithmException {
        final MessageDigest md = MessageDigest.getInstance("MD5");
        final byte[] messageDigest = md.digest(input.getBytes());
        final BigInteger number = new BigInteger(1, messageDigest);
        return String.format("%032x", number);
    }


}
