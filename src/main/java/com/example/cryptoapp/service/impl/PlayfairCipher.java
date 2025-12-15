package com.example.cryptoapp.service.impl;

public class PlayfairCipher {
    private static final int GRID_SIZE = 5;
    private char[][] keyMatrix;
    private int[][] positions;

    public PlayfairCipher(String keyword) {
        this.positions = new int[26][2];
        buildKeyMatrix(keyword);
    }

    private void buildKeyMatrix(String keyword) {
        keyMatrix = new char[GRID_SIZE][GRID_SIZE];
        boolean[] used = new boolean[26];
        int idx = 0;

        String key = (keyword == null ? "" : keyword)
                .replaceAll("[^A-Za-z]", "").toUpperCase();
        for (char c : key.toCharArray()) {
            int pos = c - 'A';
            if (!used[pos]) {
                if (idx / GRID_SIZE >= GRID_SIZE) break;
                keyMatrix[idx / GRID_SIZE][idx % GRID_SIZE] = c;
                positions[pos][0] = idx / GRID_SIZE;
                positions[pos][1] = idx % GRID_SIZE;
                used[pos] = true;
                idx++;
            }
        }

        for (int i = 0; i < 26; i++) {
            if (i == 'J' - 'A') continue;
            if (!used[i]) {
                if (idx / GRID_SIZE >= GRID_SIZE) break;
                char c = (char) ('A' + i);
                keyMatrix[idx / GRID_SIZE][idx % GRID_SIZE] = c;
                positions[i][0] = idx / GRID_SIZE;
                positions[i][1] = idx % GRID_SIZE;
                used[i] = true;
                idx++;
            }
        }
    }

    public String encrypt(String plaintext) {
        String prep = preparePlaintext(plaintext);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < prep.length(); i += 2) {
            char c1 = prep.charAt(i);
            char c2 = (i + 1 < prep.length()) ? prep.charAt(i + 1) : 'X';
            sb.append(encryptPair(c1, c2));
        }
        return sb.toString();
    }

    public String decrypt(String ciphertext) {
        String prep = ciphertext.replaceAll("[^A-Z]", "");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < prep.length(); i += 2) {
            char c1 = prep.charAt(i);
            char c2 = (i + 1 < prep.length()) ? prep.charAt(i + 1) : 'X';
            sb.append(decryptPair(c1, c2));
        }
        return sb.toString();
    }

    private String preparePlaintext(String plaintext) {
        String text = plaintext.replaceAll("[^A-Za-z]", "").toUpperCase();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == 'J') c = 'I';
            sb.append(c);
            if (i + 1 < text.length() && sb.length() % 2 == 1) {
                char next = text.charAt(i + 1);
                if (next == 'J') next = 'I';
                if (c == next) sb.append('X');
            }
        }
        if (sb.length() % 2 == 1) sb.append('X');
        return sb.toString();
    }

    private String encryptPair(char c1, char c2) {
        int r1 = positions[c1 - 'A'][0];
        int col1 = positions[c1 - 'A'][1];
        int r2 = positions[c2 - 'A'][0];
        int col2 = positions[c2 - 'A'][1];

        if (r1 == r2) {
            col1 = (col1 + 1) % GRID_SIZE;
            col2 = (col2 + 1) % GRID_SIZE;
        } else if (col1 == col2) {
            r1 = (r1 + 1) % GRID_SIZE;
            r2 = (r2 + 1) % GRID_SIZE;
        } else {
            int tmp = col1;
            col1 = col2;
            col2 = tmp;
        }
        return "" + keyMatrix[r1][col1] + keyMatrix[r2][col2];
    }

    private String decryptPair(char c1, char c2) {
        int r1 = positions[c1 - 'A'][0];
        int col1 = positions[c1 - 'A'][1];
        int r2 = positions[c2 - 'A'][0];
        int col2 = positions[c2 - 'A'][1];

        if (r1 == r2) {
            col1 = (col1 - 1 + GRID_SIZE) % GRID_SIZE;
            col2 = (col2 - 1 + GRID_SIZE) % GRID_SIZE;
        } else if (col1 == col2) {
            r1 = (r1 - 1 + GRID_SIZE) % GRID_SIZE;
            r2 = (r2 - 1 + GRID_SIZE) % GRID_SIZE;
        } else {
            int tmp = col1;
            col1 = col2;
            col2 = tmp;
        }
        return "" + keyMatrix[r1][col1] + keyMatrix[r2][col2];
    }
}
