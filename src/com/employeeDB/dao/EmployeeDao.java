package com.employeeDB.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.employeeDB.config.DBConfig;
import com.employeeDB.model.Employee;

public class EmployeeDao {
   
    /**
     * Add a new employee to the database
     * @param employee Employee object to add
     * @return true if successful
     */
    public boolean addEmployee(Employee employee) {
        String sql = "INSERT INTO employees (first_name, last_name, email, department, salary, hire_date) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setString(3, employee.getEmail());
            pstmt.setString(4, employee.getDepartment());
            pstmt.setDouble(5, employee.getSalary());
            pstmt.setDate(6, Date.valueOf(employee.getHireDate()));

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                // Get generated ID
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        employee.setId(generatedKeys.getInt(1));
                    }
                }
                System.out.println("Employee added successfully! ID: " + employee.getId());
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error adding employee: " + e.getMessage());
        }
        return false;
    }

    /**
     * Retrieve all employees from database
     * @return List of all employees
     */
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY id";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                employees.add(extractEmployeeFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving employees: " + e.getMessage());
        }

        return employees;
    }

    /**
     * Get employee by ID
     * @param id Employee ID
     * @return Employee object or null if not found
     */
    public Employee getEmployeeById(int id) {
        String sql = "SELECT * FROM employees WHERE id = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractEmployeeFromResultSet(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving employee: " + e.getMessage());
        }

        return null;
    }

    /**
     * Search employees by department
     * @param department Department name
     * @return List of employees in that department
     */
    public List<Employee> getEmployeesByDepartment(String department) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE department = ? ORDER BY last_name";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, department);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    employees.add(extractEmployeeFromResultSet(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error searching employees: " + e.getMessage());
        }

        return employees;
    }

    /**
     * Update employee information
     * @param employee Employee object with updated information
     * @return true if successful
     */
    public boolean updateEmployee(Employee employee) {
        String sql = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, " +
                     "department = ?, salary = ?, hire_date = ? WHERE id = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setString(3, employee.getEmail());
            pstmt.setString(4, employee.getDepartment());
            pstmt.setDouble(5, employee.getSalary());
            pstmt.setDate(6, Date.valueOf(employee.getHireDate()));
            pstmt.setInt(7, employee.getId());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Employee updated successfully!");
                return true;
            } else {
                System.out.println("Employee not found with ID: " + employee.getId());
            }

        } catch (SQLException e) {
            System.err.println("Error updating employee: " + e.getMessage());
        }

        return false;
    }

    /**
     * Delete employee by ID
     * @param id Employee ID to delete
     * @return true if successful
     */
    public boolean deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Employee deleted successfully!");
                return true;
            } else {
                System.out.println("Employee not found with ID: " + id);
            }

        } catch (SQLException e) {
            System.err.println("Error deleting employee: " + e.getMessage());
        }

        return false;
    }

    /**
     * Get total employee count
     * @return number of employees
     */
    public int getEmployeeCount() {
        String sql = "SELECT COUNT(*) FROM employees";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error counting employees: " + e.getMessage());
        }

        return 0;
    }

    /**
     * Helper method to extract Employee object from ResultSet
     */
    private Employee extractEmployeeFromResultSet(ResultSet rs) throws SQLException {
        return new Employee(
            rs.getInt("id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("email"),
            rs.getString("department"),
            rs.getDouble("salary"),
            rs.getDate("hire_date").toLocalDate()
        );
    }
}
