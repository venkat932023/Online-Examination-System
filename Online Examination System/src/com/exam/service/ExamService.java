package com.exam.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.exam.model.Question;

public class ExamService {
    private static final String EXAM_FILE = "data/questions.txt";
    private static final String RESULT_FILE = "data/results.txt";

    public void initializeDefaultQuestions() {
        File file = new File(EXAM_FILE);
        File parent = file.getParentFile();
        if (parent != null) parent.mkdirs();

        List<Question> defaultQuestions = new ArrayList<>();
        defaultQuestions.add(new Question("Q1", "Which keyword is used to inherit a class in Java?",
            List.of("A. interface", "B. extends", "C. implements", "D. imports"), "B", 1));
        defaultQuestions.add(new Question("Q2", "Which collection stores unique elements only?",
            List.of("A. ArrayList", "B. LinkedList", "C. HashSet", "D. Vector"), "C", 1));
        defaultQuestions.add(new Question("Q3", "What is the entry point of a Java application?",
            List.of("A. main()", "B. start()", "C. init()", "D. run()"), "A", 1));
        defaultQuestions.add(new Question("Q4", "Which Java type stores whole numbers?",
            List.of("A. double", "B. int", "C. boolean", "D. char"), "B", 1));
        defaultQuestions.add(new Question("Q5", "What executes Java bytecode?",
            List.of("A. JVM", "B. Javadoc", "C. Git", "D. HTML"), "A", 1));
        defaultQuestions.add(new Question("Q6", "Which keyword creates an object?",
            List.of("A. make", "B. create", "C. new", "D. object"), "C", 1));
        defaultQuestions.add(new Question("Q7", "What is the first index of a Java array?",
            List.of("A. 1", "B. -1", "C. 0", "D. It depends"), "C", 1));
        defaultQuestions.add(new Question("Q8", "Which access modifier makes a member accessible only in its class?",
            List.of("A. public", "B. protected", "C. private", "D. static"), "C", 1));
        defaultQuestions.add(new Question("Q9", "Which keyword declares a class that cannot be inherited?",
            List.of("A. final", "B. static", "C. const", "D. sealed"), "A", 1));
        defaultQuestions.add(new Question("Q10", "Which keyword is used when a class implements an interface?",
            List.of("A. extends", "B. implements", "C. interface", "D. imports"), "B", 1));
        defaultQuestions.add(new Question("Q11", "What is a constructor's name in Java?",
            List.of("A. Any name", "B. The package name", "C. The class name", "D. main"), "C", 1));
        defaultQuestions.add(new Question("Q12", "Which block handles an exception?",
            List.of("A. catch", "B. select", "C. repeat", "D. switch"), "A", 1));
        defaultQuestions.add(new Question("Q13", "Which collection is a resizable list?",
            List.of("A. HashSet", "B. ArrayList", "C. HashMap", "D. TreeSet"), "B", 1));
        defaultQuestions.add(new Question("Q14", "How many classes can a Java class directly extend?",
            List.of("A. None", "B. Two", "C. One", "D. Any number"), "C", 1));
        defaultQuestions.add(new Question("Q15", "Which method prints text without adding a new line?",
            List.of("A. println()", "B. printLine()", "C. writeLine()", "D. print()"), "D", 1));

        saveQuestions(defaultQuestions);
    }

    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        File file = new File(EXAM_FILE);
        if (!file.exists()) {
            initializeDefaultQuestions();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts;
                if (line.contains("|")) {
                    parts = line.split("\\|");
                } else {
                    parts = line.split("::");
                }

                if (parts.length >= 5) {
                    Question q = new Question();
                    q.setId(parts[0]);
                    q.setQuestionText(parts[1]);
                    List<String> options = new ArrayList<>();
                    if (parts.length == 5 && parts[2].contains("::")) {
                        String[] optionTokens = parts[2].split("::");
                        for (String option : optionTokens) {
                            options.add(option);
                        }
                        q.setCorrectAnswer(parts[3]);
                        q.setMarks(Integer.parseInt(parts[4]));
                    } else {
                        for (int i = 2; i < parts.length - 2; i++) {
                            options.add(parts[i]);
                        }
                        q.setCorrectAnswer(parts[parts.length - 2]);
                        q.setMarks(Integer.parseInt(parts[parts.length - 1]));
                    }
                    q.setOptions(options);
                    questions.add(q);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading exam questions: " + e.getMessage());
        }

        if (questions.isEmpty()) {
            initializeDefaultQuestions();
            return getQuestions();
        }

        return questions;
    }

    public void saveQuestions(List<Question> questions) {
        File file = new File(EXAM_FILE);
        File parent = file.getParentFile();
        if (parent != null) parent.mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Question q : questions) {
                StringBuilder sb = new StringBuilder();
                sb.append(q.getId()).append("|");
                sb.append(q.getQuestionText()).append("|");
                for (int i = 0; i < q.getOptions().size(); i++) {
                    sb.append(q.getOptions().get(i));
                    if (i < q.getOptions().size() - 1) sb.append("::");
                }
                sb.append("|").append(q.getCorrectAnswer()).append("|").append(q.getMarks());
                writer.write(sb.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving exam questions: " + e.getMessage());
        }
    }

    public void takeExam(Scanner scanner, String username) {
        initializeDefaultQuestions();
        List<Question> questions = getQuestions();

        System.out.println("\n===== Online Examination =====");
        int score = 0;
        int totalMarks = 0;

        for (Question q : questions) {
            totalMarks += q.getMarks();
            System.out.println("\n" + q.getId() + ": " + q.getQuestionText());
            for (String option : q.getOptions()) {
                System.out.println("  " + option);
            }
            System.out.print("Your answer (A/B/C/D): ");
            String answer = scanner.nextLine().trim().toUpperCase();
            if (answer.equalsIgnoreCase(q.getCorrectAnswer())) {
                score += q.getMarks();
                System.out.println("Correct!");
            } else {
                System.out.println("Wrong! Correct answer: " + q.getCorrectAnswer());
            }
        }

        double percentage = totalMarks == 0 ? 0 : (score * 100.0) / totalMarks;
        System.out.println("\nExam completed for " + username + ".");
        System.out.println("Score: " + score + " / " + totalMarks + " marks");
        System.out.printf("Percentage: %.2f%%\n", percentage);

        saveResult(username, score, totalMarks, percentage);
    }

    private void saveResult(String username, int score, int totalMarks, double percentage) {
        File file = new File(RESULT_FILE);
        File parent = file.getParentFile();
        if (parent != null) parent.mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(username + "::" + score + "::" + totalMarks + "::" + String.format("%.2f", percentage));
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving exam result: " + e.getMessage());
        }
    }
}
