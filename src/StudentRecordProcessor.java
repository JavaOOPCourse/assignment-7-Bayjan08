import java.io.*;
import java.util.*;

public class StudentRecordProcessor {

    // Поля для хранения данных
    private final List<Student> students = new ArrayList<>();

    private double averageScore;
    private Student highestStudent;

    /**
     * Task 1 + Task 2 + Task 5 + Task 6
     */
    public void readFile() {
        String inputPath = "input/students.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(",");

                    if (parts.length != 2) {
                        System.out.println("Invalid data: " + line);
                        continue;
                    }

                    String name = parts[0].trim();
                    int score = Integer.parseInt(parts[1].trim());

                    // Custom exception
                    if (score < 0 || score > 100) {
                        throw new InvalidScoreException("Invalid score: " + line);
                    }

                    students.add(new Student(name, score));
                    System.out.println("Valid: " + line);

                } catch (NumberFormatException e) {
                    System.out.println("Invalid data: " + line);
                } catch (InvalidScoreException e) {
                    System.out.println(e.getMessage());
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + inputPath);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Task 3 + Task 8
     */
    public void processData() {
        if (students.isEmpty()) {
            System.out.println("No valid student data.");
            return;
        }

        // Sort descending
        students.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));

        // Highest
        highestStudent = students.get(0);

        // Average
        int sum = 0;
        for (Student s : students) {
            sum += s.getScore();
        }

        averageScore = (double) sum / students.size();
    }

    /**
     * Task 4 + Task 5 + Task 8
     */
    public void writeFile() {
        String outputPath = "output/report.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {

            writer.write("Average: " + averageScore);
            writer.newLine();

            if (highestStudent != null) {
                writer.write("Highest: " + highestStudent.getName() + " - " + highestStudent.getScore());
                writer.newLine();
            }

            writer.newLine();
            writer.write("Sorted Students:");
            writer.newLine();

            for (Student s : students) {
                writer.write(s.getName() + " - " + s.getScore());
                writer.newLine();
            }

        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        StudentRecordProcessor processor = new StudentRecordProcessor();

        try {
            processor.readFile();
            processor.processData();
            processor.writeFile();
            System.out.println("Processing completed. Check output/report.txt");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}

// Custom Exception
class InvalidScoreException extends Exception {
    public InvalidScoreException(String message) {
        super(message);
    }
}

// Student class
class Student {
    private String name;
    private int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}