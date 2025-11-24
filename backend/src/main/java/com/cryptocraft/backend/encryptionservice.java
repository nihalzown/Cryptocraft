package com.cryptocraft.backend;

import org.springframework.stereotype.Service;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service


public class encryptionservice {
    // 1. ceasar cipher
    public String encryptceasar(String text , int shift){
        StringBuilder st = new StringBuilder();
        for(char c : text.toCharArray()){
            if(Character.isUpperCase(c)){
                st.append((char)(((c-'A'+shift)%26)+'A'));
            }else if (Character.isLowerCase(c)){
                st.append((char)(((c-'a'+shift)%26)+'a'));
            }else {
                st.append(c);
            }
        }
        return st.toString();
    }

    //2. monoalphabetic cipher
    private static String alfa = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String encrypt(String text ,String key){
        text = text.toUpperCase();
        StringBuilder ct = new StringBuilder();
        for(char c : text.toCharArray()){
            int idx = alfa.indexOf(c);
            if(idx!=-1){
                ct.append(key.charAt(idx));
            }
            else{
                    ct.append(c);
            }
        }
        return ct.toString();
    }

    //3. playfair cipher
    private static void generateKeyMatrix(String key) {
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
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                matrix[i][j] = sb.charAt(k++);
    }

    private static int[] findPosition(char c) {
        if (c == 'J') c = 'I';
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                if (matrix[i][j] == c)
                    return new int[]{i, j};
        return null;
    }

    private static String prepareText(String text) {
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
        if (sb.length() % 2 != 0)
            sb.append('X');
        return sb.toString();
    }

    public static String playfairEncrypt(String text, String key) {
        generateKeyMatrix(key);
        text = prepareText(text);
        StringBuilder cipher = new StringBuilder();
        for (int i = 0; i < text.length(); i += 2) {
            int[] pos1 = findPosition(text.charAt(i));
            int[] pos2 = findPosition(text.charAt(i + 1));
            if (pos1[0] == pos2[0]) { // Same row
                cipher.append(matrix[pos1[0]][(pos1[1] + 1) % 5]);
                cipher.append(matrix[pos2[0]][(pos2[1] + 1) % 5]);
            } else if (pos1[1] == pos2[1]) { // Same column
                cipher.append(matrix[(pos1[0] + 1) % 5][pos1[1]]);
                cipher.append(matrix[(pos2[0] + 1) % 5][pos2[1]]);
            } else { // Rectangle swap
                cipher.append(matrix[pos1[0]][pos2[1]]);
                cipher.append(matrix[pos2[0]][pos1[1]]);
            }
        }
        return cipher.toString();
    }




}
