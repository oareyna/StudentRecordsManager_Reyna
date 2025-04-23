import java.util.Arrays;

/**
 * Class representing a student record with grades.
 * This class demonstrates basic OOP principles and data encapsulation.
 */
public class Student {
    // Private fields for student data
    private String studentId;
    private String name;
    private int[] grades;
    private double averageGrade;
    private char letterGrade;

    /**
     * Constructor to initialize a Student object
     * @param studentId The student's ID
     * @param name The student's name
     * @param grades Array of the student's grades
     */
    public Student(String studentId, String name, int[] grades) {
        this.studentId = studentId;
        this.name = name;
        this.grades = grades.clone(); // clone for safety
        this.averageGrade = calculateAverage();
        this.letterGrade = determineLetterGrade();
    }

    /**
     * Calculates the average of all grades
     * @return The average grade as a double
     */
    private double calculateAverage() {
        int sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        return (double) sum / grades.length;
    }

    /**
     * Determines the letter grade based on the average
     * @return A character representing the letter grade (A, B, C, D, or F)
     */
    private char determineLetterGrade() {
        if (averageGrade >= 90) return 'A';
        else if (averageGrade >= 80) return 'B';
        else if (averageGrade >= 70) return 'C';
        else if (averageGrade >= 60) return 'D';
        else return 'F';
    }

    // Getters
    public String getId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public int[] getGrades() {
        return grades.clone(); // return a copy to preserve encapsulation
    }

    public double getAverage() {
        return averageGrade;
    }

    public char getLetterGrade() {
        return letterGrade;
    }

    /**
     * Returns a string representation of the student
     * @return String containing all student information
     */
    @Override
    public String toString() {
        return String.format(
                "Student ID: %s%nStudent Name: %s%nGrades: %s%nAverage Grade: %.2f%nLetter Grade: %c",
                studentId, name, Arrays.toString(grades), averageGrade, letterGrade
        );
    }
}
