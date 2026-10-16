package com.exam;

import com.exam.exceptions.InvalidLoginException;
import com.exam.model.Record;
import com.exam.model.User;
import com.exam.service.AuthService;
import com.exam.service.ExamService;
import com.exam.service.RecordService;
import com.exam.util.FileStorage;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        FileStorage fileStorage = new FileStorage();
        AuthService authService = new AuthService(fileStorage);
        RecordService recordService = new RecordService(fileStorage);
        ExamService examService = new ExamService();

        System.out.println("==========================================");
        System.out.println("      ONLINE EXAMINATION SYSTEM");
        System.out.println("==========================================");

        User loggedInUser = null;
        boolean running = true;

        while (running) {
            showMainMenu();
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    registerUser(authService);
                    break;
                case "2":
                    loggedInUser = loginUser(authService);
                    break;
                case "3":
                    if (loggedInUser == null) {
                        System.out.println("Please log in first.");
                    } else {
                        addRecord(recordService);
                    }
                    break;
                case "4":
                    if (loggedInUser == null) {
                        System.out.println("Please log in first.");
                    } else {
                        updateRecord(recordService);
                    }
                    break;
                case "5":
                    if (loggedInUser == null) {
                        System.out.println("Please log in first.");
                    } else {
                        viewReports(recordService, authService, loggedInUser);
                    }
                    break;
                case "6":
                    if (loggedInUser == null) {
                        System.out.println("Please log in first.");
                    } else {
                        examService.takeExam(scanner, loggedInUser.getUsername());
                    }
                    break;
                case "7":
                    if (loggedInUser == null) {
                        System.out.println("Please log in first.");
                    } else {
                        adminOperations(authService, recordService, loggedInUser);
                    }
                    break;
                case "8":
                    System.out.println("Thank you for using the Online Examination System.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\nMain Menu");
        System.out.println("1. User Registration");
        System.out.println("2. User Login");
        System.out.println("3. Add Records");
        System.out.println("4. Update Records");
        System.out.println("5. View Reports");
        System.out.println("6. Conduct Exam");
        System.out.println("7. Admin Operations");
        System.out.println("8. Exit");
    }

    private static void registerUser(AuthService authService) {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            authService.registerUser(username, password);
            System.out.println("Registration successful.");
        } catch (IllegalArgumentException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    private static User loginUser(AuthService authService) {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            User user = authService.login(username, password);
            System.out.println("Login successful. Welcome, " + user.getUsername() + "!");
            return user;
        } catch (InvalidLoginException e) {
            System.out.println("Login failed: " + e.getMessage());
            return null;
        }
    }

    private static void addRecord(RecordService recordService) {
        try {
            System.out.print("Enter record ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            System.out.print("Enter contact number: ");
            String contact = scanner.nextLine();
            System.out.print("Enter category: ");
            String category = scanner.nextLine();
            System.out.print("Enter status: ");
            String status = scanner.nextLine();

            Record record = new Record(id, name, contact, category, status);
            recordService.add(record);
            System.out.println("Record added successfully.");
        } catch (Exception e) {
            System.out.println("Failed to add record: " + e.getMessage());
        }
    }

    private static void updateRecord(RecordService recordService) {
        try {
            System.out.print("Enter record ID to update: ");
            String id = scanner.nextLine();
            Record existing = recordService.findById(id);
            if (existing == null) {
                System.out.println("Record not found.");
                return;
            }

            System.out.print("Enter new name: ");
            String name = scanner.nextLine();
            System.out.print("Enter new contact number: ");
            String contact = scanner.nextLine();
            System.out.print("Enter new category: ");
            String category = scanner.nextLine();
            System.out.print("Enter new status: ");
            String status = scanner.nextLine();

            Record updated = new Record(id, name, contact, category, status);
            recordService.update(id, updated);
            System.out.println("Record updated successfully.");
        } catch (Exception e) {
            System.out.println("Failed to update record: " + e.getMessage());
        }
    }

    private static void viewReports(RecordService recordService, AuthService authService, User user) {
        System.out.println(recordService.generateReport());
        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            System.out.println("Registered users: " + authService.getAllUsers().size());
        }
    }

    private static void adminOperations(AuthService authService, RecordService recordService, User user) {
        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            System.out.println("Access denied. Admin rights required.");
            return;
        }

        System.out.println("\nAdmin Operations");
        System.out.println("1. View all registered users");
        System.out.println("2. Show system reports");
        System.out.println("3. Search records");
        System.out.print("Choose admin option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                List<User> users = authService.getAllUsers();
                for (User currentUser : users) {
                    System.out.println(currentUser);
                }
                break;
            case "2":
                System.out.println(recordService.generateReport());
                break;
            case "3":
                System.out.print("Enter search keyword: ");
                String keyword = scanner.nextLine();
                List<Record> results = recordService.search(keyword);
                if (results.isEmpty()) {
                    System.out.println("No matching records found.");
                } else {
                    for (Record result : results) {
                        System.out.println(result);
                    }
                }
                break;
            default:
                System.out.println("Invalid admin option.");
        }
    }
}
