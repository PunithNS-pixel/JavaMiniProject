package com.example.cryptoapp.service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TokenStore {
    public static class MessageRecord {
        public final String token;
        public final String cipherType; // VIGENERE or CAESAR
        public final String encryptedText;
        public final String phrase; // for vigenere (original phrase)
        public final Integer shift;  // for caesar
        public final Instant createdAt;

        public MessageRecord(String token, String cipherType, String encryptedText, String phrase, Integer shift) {
            this.token = token;
            this.cipherType = cipherType;
            this.encryptedText = encryptedText;
            this.phrase = phrase;
            this.shift = shift;
            this.createdAt = Instant.now();
        }
    }

    private final Map<String, MessageRecord> store = new ConcurrentHashMap<>();

    public String put(String cipherType, String encryptedText, String phrase, Integer shift) {
        String token = UUID.randomUUID().toString();
        store.put(token, new MessageRecord(token, cipherType, encryptedText, phrase, shift));
        return token;
    }

    public Optional<MessageRecord> get(String token) {
        return Optional.ofNullable(store.get(token));
    }
}
