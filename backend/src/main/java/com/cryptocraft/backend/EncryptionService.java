package com.cryptocraft.backend;

import org.springframework.stereotype.Service;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

@Service
public class EncryptionService {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private char[][] playfairMatrix = new char[5][5];
    // 1. Caesar cipher
    public String encryptCaesar(String text, int shift) {
        StringBuilder result = new StringBuilder();
        shift = ((shift%26)+26)%26;
        
        for (char c : text.toCharArray()) {
            if (Character.isUpperCase(c)) {
                result.append((char)(((c-'A'+shift)%26)+'A'));
            } else if (Character.isLowerCase(c)) {
                result.append((char)(((c-'a'+shift)%26)+'a'));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    // 2. Monoalphabetic cipher
    public String encryptSubstitution(String text, String key) {
        if (key == null || key.length() != 26) {
            throw new IllegalArgumentException("Key must be exactly 26 characters long");
        }
        
        text = text.toUpperCase();
        key = key.toUpperCase();
        StringBuilder cipherText = new StringBuilder();
        
        for (char c : text.toCharArray()) {
            int index = ALPHABET.indexOf(c);
            if (index != -1) {
                cipherText.append(key.charAt(index));
            } else {
                cipherText.append(c);
            }
        }
        return cipherText.toString();
    }

    // 3. Playfair cipher
    public String playfairEncrypt(String text, String key) {
        generateKeyMatrix(key, playfairMatrix);
        text = prepareText(text);
        StringBuilder cipher = new StringBuilder();
        
        for (int i = 0; i < text.length(); i += 2) {
            int[] pos1 = findPosition(text.charAt(i));
            int[] pos2 = findPosition(text.charAt(i + 1));
            
            if (pos1[0] == pos2[0]) { // Same row
                cipher.append(playfairMatrix[pos1[0]][(pos1[1] + 1) % 5]);
                cipher.append(playfairMatrix[pos2[0]][(pos2[1] + 1) % 5]);
            } else if (pos1[1] == pos2[1]) { // Same column
                cipher.append(playfairMatrix[(pos1[0] + 1) % 5][pos1[1]]);
                cipher.append(playfairMatrix[(pos2[0] + 1) % 5][pos2[1]]);
            } else { // Rectangle swap
                cipher.append(playfairMatrix[pos1[0]][pos2[1]]);
                cipher.append(playfairMatrix[pos2[0]][pos1[1]]);
            }
        }
        return cipher.toString();
    }
    private void generateKeyMatrix(String key, char[][] matrix) {
        key = key.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        StringBuilder sb = new StringBuilder();
        Set<Character> used = new HashSet<>();
        
        for (char c : key.toCharArray()) {
            if (!used.contains(c)) {
                sb.append(c);
                used.add(c);
            }
        }
        
        for (char c = 'A'; c <= 'Z'; c++) {
            if (c != 'J' && !used.contains(c)) {
                sb.append(c);
                used.add(c);
            }
        }
        
        int k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = sb.charAt(k++);
            }
        }
    }

    private int[] findPosition(char c) {
        if (c == 'J') c = 'I';
        for (int i=0; i<5;i++) {
            for (int j=0;j<5; j++) {
                if (playfairMatrix[i][j] == c) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("Character not found in matrix: " + c);
    }

    private String prepareText(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        StringBuilder sb = new StringBuilder();
        int i = 0;
        
        while (i < text.length()) {
            char a = text.charAt(i);
            char b = (i + 1 < text.length()) ? text.charAt(i + 1) : 'X';
            
            if (a == b) {
                sb.append(a).append('X');
                i++;
            } else {
                sb.append(a).append(b);
                i += 2;
            }
        }
        
        if (sb.length() % 2 != 0) {
            sb.append('X');
        }
        return sb.toString();
    }

    // DES encryption
    public String encryptDES(String plaintext, String secretKey) throws Exception {
        byte[] keyBytes = Arrays.copyOf(secretKey.getBytes(StandardCharsets.UTF_8), 8);
        SecretKey key = new SecretKeySpec(keyBytes, "DES");

        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    // AES encryption
    public String encryptAES(String plaintext, String secretKey) throws Exception {
        byte[] keyBytes = Arrays.copyOf(secretKey.getBytes(StandardCharsets.UTF_8), 16);   
        SecretKey key = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.getEncoder().encodeToString(cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8)));
    }


    // RC4 encryption
    public String encryptRC4(String plaintext, String secretKey) throws Exception {
        byte[] keyBytes = Arrays.copyOf(secretKey.getBytes(StandardCharsets.UTF_8), 8);
        SecretKey key = new SecretKeySpec(keyBytes, "RC4");

        Cipher cipher = Cipher.getInstance("RC4");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    // blowfish encryption
    public String encryptBlowfish(String plaintext, String secretKey) throws Exception {
        byte[] keyBytes = Arrays.copyOf(secretKey.getBytes(StandardCharsets.UTF_8), 8);
        SecretKey key = new SecretKeySpec(keyBytes, "Blowfish");

        Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }
}
