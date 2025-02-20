import java.io.*;
import java.util.*;

// Abstract class representing a University Member
abstract class UniversityMember {
    protected String name;
    protected int id;

    public UniversityMember(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public abstract void performRole();

    public void showDetails() {
        System.out.println("\n--- Member Details ---");
        System.out.println("Name: " + name + " | ID: " + id);
    }
}

// Inheritance: Base class for university staff
abstract class UniversityStaff extends UniversityMember {
    protected String department;

    public UniversityStaff(String name, int id, String department) {
        super(name, id);
        this.department = department;
    }
}

interface Researcher {
    void conductResearch();
}

interface Mentor {
    void guideStudents();
}

class Student extends UniversityMember {
    private int age;

    public Student(String name, int id, int age) {
        super(name, id);
        setAge(age);
    }

    public void setAge(int age) {
        if (age > 0) {
            this.age = age;
        } else {
            System.out.println("Age cannot be negative or zero");
        }
    }

    public void performRole() {
        System.out.println(name + " is currently enrolled as a student.");
    }
}

class Professor extends UniversityStaff implements Researcher, Mentor {
    private String specialization;

    public Professor(String name, int id, String department, String specialization) {
        super(name, id, department);
        this.specialization = specialization;
    }

    public void performRole() {
        System.out.println(name + " is a professor specializing in " + specialization);
    }

    public void conductResearch() {
        System.out.println(name + " is conducting research in " + specialization);
    }

    public void guideStudents() {
        System.out.println(name + " is guiding students in " + specialization);
    }
}

class AdminStaff extends UniversityStaff {
    private String role;

    public AdminStaff(String name, int id, String department, String role) {
        super(name, id, department);
        this.role = role;
    }

    public void performRole() {
        System.out.println(name + " is responsible for " + role + " at the university.");
    }
}

class Course {
    private String courseName;
    private List<String> enrolledStudents = new ArrayList<>();

    public Course(String courseName) {
        this.courseName = courseName;
    }

    public void enrollStudent(String studentName) {
        enrolledStudents.add(studentName);
        System.out.println(studentName + " has been enrolled in " + courseName);
    }

    public void enrollStudent(String studentName, int studentID) {
        enrolledStudents.add(studentName + " (ID: " + studentID + ")");
        System.out.println("Student ID: " + studentID + " (" + studentName + ") has been enrolled in " + courseName);
    }
}

class ReportGenerator {
    public static void generateReport(String filename) {
        File file = new File(filename);

        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("No data available in " + filename + ".");
                return;
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
                return;
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            System.out.println("\n--- " + filename.toUpperCase() + " Report ---");
            String line;
            boolean isEmpty = true;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                isEmpty = false;
            }
            if (isEmpty) {
                System.out.println("No data available in " + filename + ".");
            }
        } catch (IOException e) {
            System.out.println("Error reading " + filename + ": " + e.getMessage());
        }
    }
}

public class UniversityManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Student Input with validation
        String studentName = getValidStringInput("Enter Student Name: ");
        int studentID = getValidIntegerInput("Enter Student ID: ");
        int studentAge = getValidIntegerInput("Enter Student Age: ");
        Student student = new Student(studentName, studentID, studentAge);

        // Professor Input with validation
        String professorName = getValidStringInput("Enter Professor Name: ");
        int professorID = getValidIntegerInput("Enter Professor ID: ");
        String department = getValidDepartmentInput();
        String specialization = getValidStringInput("Enter Specialization: ");
        Professor professor = new Professor(professorName, professorID, department, specialization);

        // Course Input
        String courseName = getValidStringInput("Enter Course Name: ");
        Course course = new Course(courseName);
        course.enrollStudent(studentName, studentID);

        // Save to files
        saveToFile("students.txt", studentName + " (ID: " + studentID + ")");
        saveToFile("staff.txt", professorName + " (ID: " + professorID + ") - " + specialization);
        saveToFile("courses.txt", courseName);

        // Perform actions
        student.performRole();
        professor.performRole();
        professor.conductResearch();
        professor.guideStudents();

        // Generate Reports
        ReportGenerator.generateReport("students.txt");
        ReportGenerator.generateReport("staff.txt");
        ReportGenerator.generateReport("courses.txt");
    }

    // Method to get a valid string input
    public static String getValidStringInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (input.trim().isEmpty()) {
            System.out.print(prompt);
            input = scanner.nextLine();
            if (input.trim().isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            }
        }
        return input;
    }

    // Method to get a valid integer input
    public static int getValidIntegerInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        int input = -1;
        while (input <= 0) {
            try {
                System.out.print(prompt);
                input = Integer.parseInt(scanner.nextLine());
                if (input <= 0) {
                    System.out.println("Please enter a positive integer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return input;
    }

    // Method to get a valid department name
    public static String getValidDepartmentInput() {
        String department = getValidStringInput("Enter Department: ");
        List<String> validDepartments = Arrays.asList("computer Science", "mathematics", "physics", "electronics", "at", "aerospace");
        while (!validDepartments.contains(department.trim())) {
            System.out.println("Invalid department. Valid departments are: " + validDepartments);
            department = getValidStringInput("Enter Department: ");
        }
        return department;
    }

    // Method to save data to file
    public static void saveToFile(String filename, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to " + filename + ": " + e.getMessage());
        }
    }
}
