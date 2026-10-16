package com.exam.util;

import com.exam.model.Record;
import com.exam.model.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {
    private final Path baseDir;
    private final Path usersFile;
    private final Path recordsFile;

    public FileStorage() {
        this.baseDir = Paths.get("data");
        this.usersFile = baseDir.resolve("users.txt");
        this.recordsFile = baseDir.resolve("records.txt");
        ensureFiles();
    }

    private void ensureFiles() {
        try {
            Files.createDirectories(baseDir);
            if (Files.notExists(usersFile)) {
                Files.writeString(usersFile, "", StandardCharsets.UTF_8);
            }
            if (Files.notExists(recordsFile)) {
                Files.writeString(recordsFile, "", StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to initialize storage files.", e);
        }
    }

    public void saveUsers(List<User> users) {
        try {
            StringBuilder content = new StringBuilder();
            for (User user : users) {
                content.append(user.getUsername())
                        .append(",")
                        .append(user.getPassword())
                        .append(",")
                        .append(user.getRole())
                        .append(System.lineSeparator());
            }
            Files.writeString(usersFile, content.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save users.", e);
        }
    }

    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        if (Files.notExists(usersFile)) {
            return users;
        }

        try {
            List<String> lines = Files.readAllLines(usersFile, StandardCharsets.UTF_8);
            for (String line : lines) {
                if (line == null || line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    User user = new User(parts[0], parts[1], parts[2]);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load users.", e);
        }
        return users;
    }

    public void saveRecords(List<Record> records) {
        try {
            StringBuilder content = new StringBuilder();
            for (Record record : records) {
                content.append(record.getId())
                        .append(",")
                        .append(record.getName())
                        .append(",")
                        .append(record.getContactNumber())
                        .append(",")
                        .append(record.getCategory())
                        .append(",")
                        .append(record.getStatus())
                        .append(System.lineSeparator());
            }
            Files.writeString(recordsFile, content.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save records.", e);
        }
    }

    public List<Record> loadRecords() {
        List<Record> records = new ArrayList<>();
        if (Files.notExists(recordsFile)) {
            return records;
        }

        try {
            List<String> lines = Files.readAllLines(recordsFile, StandardCharsets.UTF_8);
            for (String line : lines) {
                if (line == null || line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    records.add(new Record(parts[0], parts[1], parts[2], parts[3], parts[4]));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load records.", e);
        }
        return records;
    }
}
