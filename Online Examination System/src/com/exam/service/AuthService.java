package com.exam.service;

import com.exam.exceptions.InvalidLoginException;
import com.exam.model.User;
import com.exam.util.FileStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthService {
    private final Map<String, User> users = new HashMap<>();
    private final FileStorage storage;

    public AuthService(FileStorage storage) {
        this.storage = storage;
        loadUsers();
        ensureAdminExists();
    }

    private void loadUsers() {
        List<User> savedUsers = storage.loadUsers();
        for (User user : savedUsers) {
            users.put(user.getUsername(), user);
        }
    }

    private void ensureAdminExists() {
        if (!users.containsKey("admin")) {
            users.put("admin", new User("admin", "admin123", "ADMIN"));
            saveUsers();
        }
    }

    public void registerUser(String username, String password) {
        String trimmedUsername = username.trim();
        String trimmedPassword = password.trim();

        if (trimmedUsername.isEmpty() || trimmedPassword.isEmpty()) {
            throw new IllegalArgumentException("Username and password cannot be empty.");
        }

        if (users.containsKey(trimmedUsername)) {
            throw new IllegalArgumentException("Username already exists. Please choose another one.");
        }

        users.put(trimmedUsername, new User(trimmedUsername, trimmedPassword, "USER"));
        saveUsers();
    }

    public User login(String username, String password) throws InvalidLoginException {
        User foundUser = users.get(username.trim());
        if (foundUser == null) {
            throw new InvalidLoginException("User not found.");
        }
        if (!foundUser.getPassword().equals(password)) {
            throw new InvalidLoginException("Invalid password.");
        }
        return foundUser;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public void saveUsers() {
        storage.saveUsers(new ArrayList<>(users.values()));
    }
}
