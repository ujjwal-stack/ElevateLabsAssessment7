package com.employeeDB;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import com.employeeDB.config.DBConfig;
import com.employeeDB.dao.EmployeeDao;
import com.employeeDB.model.Employee;

public class EmployeeMain {
    private static final Scanner sc = new Scanner(System.in);
    private static final EmployeeDao employeeDAO = new EmployeeDao();
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        // Display welcome banner
        displayWelcomeBanner();

        // Initialize database
        System.out.println("\nInitializing database...");
        DBConfig.initializeDatabase();

        // Test connection
        if (!DBConfig.testConnection()) {
            System.err.println("\nFailed to connect to database. Please check your configuration.");
            System.err.println("Make sure MySQL is running and credentials in DatabaseConfig.java are correct.");
            return;
        }

        System.out.println("Connected to database successfully!\n");

        // Main application loop
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1 : addEmployee(); break;
                case 2 : viewAllEmployees(); break;
                case 3 : searchEmployeeById(); break;
                case 4 : searchEmployeesByDepartment(); break;
                case 5 : updateEmployee(); break;
                case 6 : deleteEmployee(); break;
                case 7 : displayStatistics(); break;
                case 8 : {
                    System.out.println("\nThank you for using Employee Management System!");
                    running = false;
                }
                default : System.out.println("\nInvalid choice! Please try again.");
            }
        }

        sc.close();
    }

     /**
     * Display welcome banner
     */
    private static void displayWelcomeBanner() {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                              ║");
        System.out.println("║          EMPLOYEE MANAGEMENT SYSTEM - JDBC Edition           ║");
        System.out.println("║                    Version 1.0 - 2024                        ║");
        System.out.println("║                                                              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }

    /**
     * Display main menu
     */
    private static void displayMenu() {
        System.out.println("\n" + repeat("=", 60));
        System.out.println("                        MAIN MENU");
        System.out.println(repeat("=", 60));
        System.out.println("  1. Add New Employee");
        System.out.println("  2. View All Employees");
        System.out.println("  3. Search Employee by ID");
        System.out.println("  4. Search Employees by Department");
        System.out.println("  5. Update Employee");
        System.out.println("  6. Delete Employee");
        System.out.println("  7. View Statistics");
        System.out.println("  8. Exit");
        System.out.println(repeat("=", 60));
    }

    /**
     * Add new employee
     */
    private static void addEmployee() {
        System.out.println("\n" + repeat("─", 60));
        System.out.println("               ADD NEW EMPLOYEE");
        System.out.println(repeat("─", 60));

        try {
            System.out.print("First Name: ");
            String firstName = sc.nextLine().trim();

            System.out.print("Last Name: ");
            String lastName = sc.nextLine().trim();

            System.out.print("Email: ");
            String email = sc.nextLine().trim();

            System.out.print("Department: ");
            String department = sc.nextLine().trim();

            double salary = getDoubleInput("Salary: ");

            LocalDate hireDate = getDateInput("Hire Date (yyyy-MM-dd): ");

            // Validation
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || department.isEmpty()) {
                System.out.println("All fields are required!");
                return;
            }

            if (salary <= 0) {
                System.out.println("Salary must be positive!");
                return;
            }

            Employee employee = new Employee(firstName, lastName, email, department, salary, hireDate);
            employeeDAO.addEmployee(employee);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static String repeat(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * View all employees
     */
    private static void viewAllEmployees() {
        System.out.println("\n" + repeat("─", 60));
        System.out.println("               ALL EMPLOYEES");
        System.out.println(repeat("─", 60));

        List<Employee> employees = employeeDAO.getAllEmployees();

        if (employees.isEmpty()) {
            System.out.println("No employees found in the database.");
        } else {
            System.out.println("\nTotal Employees: " + employees.size());
            System.out.println("\n" + repeat("─", 120));
            for (Employee emp : employees) {
                System.out.println(emp);
            }
            System.out.println(repeat("─", 120));
        }
    }

    /**
     * Search employee by ID
     */
    private static void searchEmployeeById() {
        System.out.println("\n" + repeat("─", 60));
        System.out.println("               SEARCH EMPLOYEE BY ID");
        System.out.println(repeat("─", 60));

        int id = getIntInput("Enter Employee ID: ");
        Employee employee = employeeDAO.getEmployeeById(id);

        if (employee != null) {
            System.out.println("\n✓ Employee Found:");
            System.out.println(repeat("─", 120));
            System.out.println(employee);
            System.out.println(repeat("─", 120));
        } else {
            System.out.println("No employee found with ID: " + id);
        }
    }

    /**
     * Search employees by department
     */
    private static void searchEmployeesByDepartment() {
        System.out.println("\n" + repeat("─", 60));
        System.out.println("          SEARCH EMPLOYEES BY DEPARTMENT");
        System.out.println(repeat("─", 60));

        System.out.print("Enter Department: ");
        String department = sc.nextLine().trim();

        List<Employee> employees = employeeDAO.getEmployeesByDepartment(department);

        if (employees.isEmpty()) {
            System.out.println("No employees found in " + department + " department.");
        } else {
            System.out.println("\n✓ Found " + employees.size() + " employee(s) in " + department);
            System.out.println(repeat("─", 120));
            for (Employee emp : employees) {
                System.out.println(emp);
            }
            System.out.println(repeat("─", 120));
        }
    }

    /**
     * Update employee
     */
    private static void updateEmployee() {
        System.out.println("\n" + repeat("─", 60));
        System.out.println("               UPDATE EMPLOYEE");
        System.out.println(repeat("─", 60));

        int id = getIntInput("Enter Employee ID to update: ");
        Employee employee =  employeeDAO.getEmployeeById(id);

        if (employee == null) {
            System.out.println("Employee not found with ID: " + id);
            return;
        }

        System.out.println("\nCurrent Details:");
        System.out.println(employee);
        System.out.println("\nEnter new details (press Enter to keep current value):");

        try {
            System.out.print("First Name [" + employee.getFirstName() + "]: ");
            String firstName = sc.nextLine().trim();
            if (!firstName.isEmpty()) employee.setFirstName(firstName);

            System.out.print("Last Name [" + employee.getLastName() + "]: ");
            String lastName = sc.nextLine().trim();
            if (!lastName.isEmpty()) employee.setLastName(lastName);

            System.out.print("Email [" + employee.getEmail() + "]: ");
            String email = sc.nextLine().trim();
            if (!email.isEmpty()) employee.setEmail(email);

            System.out.print("Department [" + employee.getDepartment() + "]: ");
            String department = sc.nextLine().trim();
            if (!department.isEmpty()) employee.setDepartment(department);

            System.out.print("Salary [" + employee.getSalary() + "]: ");
            String salaryStr = sc.nextLine().trim();
            if (!salaryStr.isEmpty()) {
                double salary = Double.parseDouble(salaryStr);
                if (salary > 0) employee.setSalary(salary);
            }

            System.out.print("Hire Date [" + employee.getHireDate() + "] (yyyy-MM-dd): ");
            String dateStr = sc.nextLine().trim();
            if (!dateStr.isEmpty()) {
                LocalDate hireDate = LocalDate.parse(dateStr, dateFormatter);
                employee.setHireDate(hireDate);
            }

            employeeDAO.updateEmployee(employee);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Delete employee
     */
    private static void deleteEmployee() {
        System.out.println("\n" + repeat("─", 60));
        System.out.println("               DELETE EMPLOYEE");
        System.out.println(repeat("─", 60));

        int id = getIntInput("Enter Employee ID to delete: ");
        Employee employee = employeeDAO.getEmployeeById(id);

        if (employee == null) {
            System.out.println("Employee not found with ID: " + id);
            return;
        }

        System.out.println("\nEmployee to delete:");
        System.out.println(employee);

        System.out.print("\nAre you sure you want to delete this employee? (yes/no): ");
        String confirmation = sc.nextLine().trim().toLowerCase();

        if (confirmation.equals("yes") || confirmation.equals("y")) {
            employeeDAO.deleteEmployee(id);
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    /**
     * Display statistics
     */
    private static void displayStatistics() {
        System.out.println("\n" + repeat("─", 60));
        System.out.println("               DATABASE STATISTICS");
        System.out.println(repeat("─", 60));

        int totalEmployees = employeeDAO.getEmployeeCount();
        System.out.println("Total Employees: " + totalEmployees);

        if (totalEmployees > 0) {
            List<Employee> employees = employeeDAO.getAllEmployees();
            
            // Calculate average salary
            double avgSalary = employees.stream()
                    .mapToDouble(Employee::getSalary)
                    .average()
                    .orElse(0.0);

            // Find max salary
            double maxSalary = employees.stream()
                    .mapToDouble(Employee::getSalary)
                    .max()
                    .orElse(0.0);

            // Find min salary
            double minSalary = employees.stream()
                    .mapToDouble(Employee::getSalary)
                    .min()
                    .orElse(0.0);

            System.out.printf("Average Salary: $%.2f%n", avgSalary);
            System.out.printf("Highest Salary: $%.2f%n", maxSalary);
            System.out.printf("Lowest Salary: $%.2f%n", minSalary);
        }
    }

    /**
     * Helper method to get integer input
     */
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = sc.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    /**
     * Helper method to get double input
     */
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = sc.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    /**
     * Helper method to get date input
     */
    private static LocalDate getDateInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = sc.nextLine().trim();
                return LocalDate.parse(input, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Please enter date in yyyy-MM-dd format!");
            }
        }
    }
    
}
