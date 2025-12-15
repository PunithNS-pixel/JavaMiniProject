package com.example.cryptoapp.service;

public interface CipherService {
    String encryptVigenere(String plaintext, String keyword);
    String decryptVigenere(String ciphertext, String keyword);

    String encryptCaesar(String plaintext, int shift);
    String decryptCaesar(String ciphertext, int shift);

    String encryptAtbash(String plaintext);
    String decryptAtbash(String ciphertext);

    String encryptPlayfair(String plaintext, String keyword);
    String decryptPlayfair(String ciphertext, String keyword);
}
