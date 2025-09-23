import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class EmployeeManagement {
    private LinkedList<Employee> employees;
    private Scanner scanner;
    private final String FILE_NAME = "employees.txt";

    public EmployeeManagement() {
        employees = new LinkedList<>();
        scanner = new Scanner(System.in);
        loadEmployeesFromFile(); // Load at startup
        runMenu();
    }

    private void runMenu() {
        int choice;
        do {
            displayMenu();
            choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> displayEmployees();
                case 3 -> removeEmployee();
                case 4 -> findEmployeesByCourse();
                case 5 -> displayStats();
                case 6 -> sortEmployees();
                case 7 -> importFromEmployeeFile();
                case 8 -> exportToEmployeeFile();
                case 9 -> {
                    saveEmployeesToFile();
                    System.out.println("Exiting program. Goodbye!");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 9);
    }

    private void displayMenu() {
        System.out.println("\n=== Employee Training Management ===");
        System.out.println("1. Add Employee");
        System.out.println("2. Display All Employees");
        System.out.println("3. Remove Employee");
        System.out.println("4. Find Employees by Course");
        System.out.println("5. Display Statistics");
        System.out.println("6. Sort Employees");
        System.out.println("7. Import Employee Data from File");
        System.out.println("8. Export Employee Data to File");
        System.out.println("9. Exit");
        System.out.println("====================================");
    }

    // MENU 1
    private void addEmployee() {
        System.out.println("\n--- Add New Employee ---");

        String empNum;
        Employee tempEmployee;
        //emp number validation
        do {
            empNum = getStringInput("Enter employee number (must start with 'emp'): ");
            tempEmployee = new Employee(empNum, "", 0, "");
            if (!empNum.toLowerCase().startsWith("emp")) {
                System.out.println("Employee number must start with 'emp'. Please try again.");
            } else if (employees.contains(tempEmployee)) {
                System.out.println("Employee number already exists. Please try again.");
            }
        } while (!empNum.toLowerCase().startsWith("emp") || employees.contains(tempEmployee));

        String name = getStringInput("Enter employee name: ");

        int years;
        do {
            years = getIntInput("Enter years of experience: ");
            if (years < 3) {
                System.out.println("Minimum 3 years experience required.");
            }
        } while (years < 3);

        //course validation
        String course;
        do {
            course = getStringInput("Enter course name (must start with 'food'): ");
            if (!course.toLowerCase().startsWith("food")) {
                System.out.println("Course name must start with 'food'.");
            }
        } while (!course.toLowerCase().startsWith("food"));

        Employee employee = new Employee(empNum, name, years, course);
        employees.add(employee);
        System.out.println("Employee added successfully!");
    }

    // MENU 2
    private void displayEmployees() {
        System.out.println("\n--- All Employees ---");
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (Employee emp : employees) {
                System.out.println(emp);
            }
        }
        waitForEnter();
    }

    // MENU 3
    private void removeEmployee() {
        System.out.println("\n--- Remove Employee ---");
        String empNum = getStringInput("Enter employee number to remove: ");
        Employee temp = new Employee(empNum, "", 0, "");
        Employee removed = null;

        for (Employee emp : employees) {
            if (emp.equals(temp)) {
                removed = emp;
                break;
            }
        }

        if (removed != null) {
            employees.remove(removed);
            System.out.println("Employee removed: " + removed.getEmployeeName());
        } else {
            System.out.println("Employee not found.");
        }
        waitForEnter();
    }

    // MENU 4
    private void findEmployeesByCourse() {
        System.out.println("\n--- Find Employees by Course ---");
        String course = getStringInput("Enter course name: ");
        LinkedList<Employee> results = new LinkedList<>();

        for (Employee emp : employees) {
            if (emp.getCourseName().equalsIgnoreCase(course)) {
                results.add(emp);
            }
        }

        if (results.isEmpty()) {
            System.out.println("No employees found for course: " + course);
        } else {
            System.out.println("Employees enrolled in " + course + ":");
            for (Employee emp : results) {
                System.out.println(emp);
            }
        }
        waitForEnter();
    }

    // MENU 5
    private void displayStats() {
        System.out.println("\n--- Statistics ---");
        System.out.println("Total employees: " + employees.size());

        int foodSafetyCount = 0, foodQualityCount = 0, otherCount = 0;

        for (Employee emp : employees) {
            String course = emp.getCourseName().toLowerCase();
            if (course.contains("safety")) foodSafetyCount++;
            else if (course.contains("quality")) foodQualityCount++;
            else otherCount++;
        }

        System.out.println("Food Safety courses: " + foodSafetyCount);
        System.out.println("Food Quality courses: " + foodQualityCount);
        System.out.println("Other courses: " + otherCount);
        waitForEnter();
    }

    // MENU 6
    private void sortEmployees() {
        System.out.println("\n--- Sort Employees ---");
        System.out.println("1. By Name");
        System.out.println("2. By Years of Experience");
        int option = getIntInput("Choose sorting option: ");

        for (int i = 0; i < employees.size() - 1; i++) {
            for (int j = 0; j < employees.size() - i - 1; j++) {
                Employee e1 = employees.get(j);
                Employee e2 = employees.get(j + 1);
                boolean shouldSwap = false;

                if (option == 1 && e1.getEmployeeName().compareToIgnoreCase(e2.getEmployeeName()) > 0) {
                    shouldSwap = true;
                } else if (option == 2 && e1.getYearsWorking() > e2.getYearsWorking()) {
                    shouldSwap = true;
                }

                if (shouldSwap) {
                    employees.set(j, e2);
                    employees.set(j + 1, e1);
                }
            }
        }

        System.out.println("Employees sorted successfully.");
        displayEmployees();
    }



    // MENU 8
    private void exportToEmployeeFile() {
        String exportFile = "employee.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(exportFile))) {
            for (Employee emp : employees) {
                writer.write(emp.getEmployeeNum() + "," + emp.getEmployeeName() + "," +
                        emp.getYearsWorking() + "," + emp.getCourseName());
                writer.newLine();
            }
            System.out.println("Employees exported successfully to " + exportFile);
        } catch (IOException e) {
            System.out.println("Error exporting employee data: " + e.getMessage());
        }
        waitForEnter();
    }

    // MENU 7
    private void importFromEmployeeFile() {
        String importFile = "employee.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(importFile))) {
            String line;
            int added = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String empNum = parts[0];
                    String name = parts[1];
                    int years = Integer.parseInt(parts[2]);
                    String course = parts[3];
                    Employee emp = new Employee(empNum, name, years, course);
                    if (!employees.contains(emp)) {
                        employees.add(emp);
                        added++;
                    }
                }
            }
            System.out.println("Imported " + added + " employee(s) from " + importFile);
        } catch (IOException e) {
            System.out.println("Error importing employee data: " + e.getMessage());
        }
        waitForEnter();
    }

    // Load from file
    private void loadEmployeesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String empNum = parts[0];
                    String name = parts[1];
                    int years = Integer.parseInt(parts[2]);
                    String course = parts[3];
                    Employee emp = new Employee(empNum, name, years, course);
                    if (!employees.contains(emp)) {
                        employees.add(emp);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("No existing data file found. Starting with empty list.");
        }
    }

    // Save to file
    private void saveEmployeesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Employee emp : employees) {
                writer.write(emp.getEmployeeNum() + "," + emp.getEmployeeName() + "," +
                        emp.getYearsWorking() + "," + emp.getCourseName());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    // Utility methods
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private void waitForEnter() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public static void main(String[] args) {
        new EmployeeManagement();
    }
}

// Employee class (can be moved to separate file if needed)
class Employee {
    private String employeeNum;
    private String employeeName;
    private int yearsWorking;
    private String courseName;

    public Employee() {}

    public Employee(String employeeNum, String employeeName, int yearsWorking, String courseName) {
        this.employeeNum = employeeNum;
        this.employeeName = employeeName;
        this.yearsWorking = yearsWorking;
        this.courseName = courseName;
    }

    public String getEmployeeNum() { return employeeNum; }
    public String getEmployeeName() { return employeeName; }
    public int getYearsWorking() { return yearsWorking; }
    public String getCourseName() { return courseName; }

    public void setEmployeeNum(String employeeNum) { this.employeeNum = employeeNum; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }
    public void setYearsWorking(int yearsWorking) { this.yearsWorking = yearsWorking; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    @Override
    public String toString() {
        return "\nEmployee Number: " + employeeNum +
               "\nName: " + employeeName +
               "\nYears of Experience: " + yearsWorking +
               "\nCourse: " + courseName + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Employee other) {
            return this.employeeNum.equalsIgnoreCase(other.employeeNum);
        }
        return false;
    }
}
