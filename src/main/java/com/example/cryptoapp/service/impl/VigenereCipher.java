package com.example.cryptoapp.service.impl;

import com.example.cryptoapp.service.CipherService;
import org.springframework.stereotype.Service;

@Service
public class VigenereCipher implements CipherService {
    private static final int ALPHABET = 26;

    private static String sanitizeKeyword(String keyword) {
        return keyword == null ? "" : keyword.replaceAll("[^A-Za-z]", "").toUpperCase();
    }

    private static char encChar(char c, char k) {
        int base = Character.isUpperCase(c) ? 'A' : 'a';
        int offset = (c - base + (Character.toUpperCase(k) - 'A')) % ALPHABET;
        return (char) (base + offset);
    }

    private static char decChar(char c, char k) {
        int base = Character.isUpperCase(c) ? 'A' : 'a';
        int offset = (c - base - (Character.toUpperCase(k) - 'A') + ALPHABET) % ALPHABET;
        return (char) (base + offset);
    }

    @Override
    public String encryptVigenere(String plaintext, String keyword) {
        String key = sanitizeKeyword(keyword);
        if (key.isEmpty()) return plaintext;
        StringBuilder sb = new StringBuilder();
        int j = 0;
        for (int i = 0; i < plaintext.length(); i++) {
            char c = plaintext.charAt(i);
            if (Character.isLetter(c)) {
                sb.append(encChar(c, key.charAt(j % key.length())));
                j++;
            } else sb.append(c);
        }
        return sb.toString();
    }

    @Override
    public String decryptVigenere(String ciphertext, String keyword) {
        String key = sanitizeKeyword(keyword);
        if (key.isEmpty()) return ciphertext;
        StringBuilder sb = new StringBuilder();
        int j = 0;
        for (int i = 0; i < ciphertext.length(); i++) {
            char c = ciphertext.charAt(i);
            if (Character.isLetter(c)) {
                sb.append(decChar(c, key.charAt(j % key.length())));
                j++;
            } else sb.append(c);
        }
        return sb.toString();
    }

    @Override
    public String encryptCaesar(String plaintext, int shift) {
        int s = ((shift % ALPHABET) + ALPHABET) % ALPHABET;
        StringBuilder sb = new StringBuilder();
        for (char c : plaintext.toCharArray()) {
            if (Character.isLetter(c)) {
                int base = Character.isUpperCase(c) ? 'A' : 'a';
                int off = (c - base + s) % ALPHABET;
                sb.append((char) (base + off));
            } else sb.append(c);
        }
        return sb.toString();
    }

    @Override
    public String decryptCaesar(String ciphertext, int shift) {
        return encryptCaesar(ciphertext, -shift);
    }

    @Override
    public String encryptAtbash(String plaintext) {
        StringBuilder sb = new StringBuilder();
        for (char c : plaintext.toCharArray()) {
            if (Character.isLetter(c)) {
                int base = Character.isUpperCase(c) ? 'A' : 'a';
                int offset = ALPHABET - 1 - (c - base);
                sb.append((char) (base + offset));
            } else sb.append(c);
        }
        return sb.toString();
    }

    @Override
    public String decryptAtbash(String ciphertext) {
        return encryptAtbash(ciphertext);
    }

    @Override
    public String encryptPlayfair(String plaintext, String keyword) {
        return new PlayfairCipher(keyword).encrypt(plaintext);
    }

    @Override
    public String decryptPlayfair(String ciphertext, String keyword) {
        return new PlayfairCipher(keyword).decrypt(ciphertext);
    }
}
