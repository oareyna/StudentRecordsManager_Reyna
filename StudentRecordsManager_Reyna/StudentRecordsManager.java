import java.io.*;
import java.util.*;


public class StudentRecordsManager {

    public static void main(String[] args) {
        StudentRecordsManager manager = new StudentRecordsManager();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter input filename: ");
        String inputFile = scanner.nextLine();

        System.out.print("Enter output filename: ");
        String outputFile = scanner.nextLine();

        try {
            manager.processStudentRecords(inputFile, outputFile);
        } catch (Exception e) {
            System.err.println("An error occurred while processing student records.");
            System.err.println("Details: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    public void processStudentRecords(String inputFile, String outputFile) {
        try {
            List<Student> students = readStudentRecords(inputFile);
            writeResultsToFile(students, outputFile);
            System.out.println("Processing complete. Results written to: " + outputFile);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            System.err.println("Please check the file path and try again.");
        } catch (IOException e) {
            System.err.println("An I/O error occurred: " + e.getMessage());
        }
    }

    public List<Student> readStudentRecords(String filename) throws IOException {
        List<Student> students = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    String[] parts = line.split(",");
                    if (parts.length < 6) {
                        throw new ArrayIndexOutOfBoundsException();
                    }

                    String studentId = parts[0].trim();
                    String name = parts[1].trim();

                    int[] grades = new int[4];
                    for (int i = 0; i < 4; i++) {
                        grades[i] = Integer.parseInt(parts[i + 2].trim());
                        if (grades[i] < 0 || grades[i] > 100) {
                            throw new InvalidGradeException("Grade out of valid range: " + grades[i]);
                        }
                    }

                    students.add(new Student(studentId, name, grades));
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format on line " + lineNumber + ": " + e.getMessage());
                } catch (InvalidGradeException e) {
                    System.err.println("Invalid grade on line " + lineNumber + ": " + e.getMessage());
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Insufficient fields on line " + lineNumber);
                }
            }
        }

        return students;
    }

    public void writeResultsToFile(List<Student> students, String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Student Records Report");
            writer.println("======================");

            int total = 0;
            int count = 0;
            int[] gradeCounts = new int[5]; // A, B, C, D, F

            for (Student s : students) {
                double avg = s.getAverage();
                total += avg;
                count++;

                char letter = s.getLetterGrade();
                switch (letter) {
                    case 'A': gradeCounts[0]++; break;
                    case 'B': gradeCounts[1]++; break;
                    case 'C': gradeCounts[2]++; break;
                    case 'D': gradeCounts[3]++; break;
                    case 'F': gradeCounts[4]++; break;
                }

                writer.printf("%s (%s): Average = %d, Grade = %c%n", s.getName(), s.getId(), avg, letter);
            }

            writer.println();
            writer.println("Class Statistics");
            writer.println("----------------");
            writer.println("Total Students: " + count);
            writer.printf("Class Average: %.2f%n", (count > 0 ? (double) total / count : 0));
            writer.println("Grade Distribution:");
            writer.printf("A: %d, B: %d, C: %d, D: %d, F: %d%n",
                    gradeCounts[0], gradeCounts[1], gradeCounts[2],
                    gradeCounts[3], gradeCounts[4]);
        }
    }
}
