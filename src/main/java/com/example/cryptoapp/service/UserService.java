package com.example.cryptoapp.service;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {
    private final Map<String, String> users = new ConcurrentHashMap<>();

    public boolean register(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank())
            return false;
        if (users.containsKey(username))
            return false;
        users.put(username, password);
        return true;
    }

    public boolean authenticate(String username, String password) {
        return password != null && password.equals(users.get(username));
    }

    public boolean userExists(String username) {
        return users.containsKey(username);
    }
}