# Employee Database Management System - JDBC Edition

A comprehensive Java-based employee management system demonstrating JDBC connectivity, CRUD operations, and professional software design patterns.

---

## 📋 Project Overview

This project implements a complete employee database management system using Java JDBC with MySQL. It showcases industry-standard practices including:

 - MVC Architecture (Model-View-Controller pattern)
 - DAO Pattern (Data Access Object for database operations)
 - JDBC Best Practices (PreparedStatements, Connection pooling considerations)
 - Input Validation and Error Handling
 - Clean Code principles with comprehensive documentation

---

## 🏗️ Project Structure

```bash

EmployDBAssessment/
│
├── src/
│   └── com/
│       └── employeedb/
│           ├── EmployeeMain.java    # Main application entry point
│           ├── model/
│           │   └── Employee.java              # Employee entity class
│           ├── dao/
│           │   └── EmployeeDAO.java           # Database operations layer
│           └── config/
│               └── DBConfig.java        # Database configuration
│
├── lib/
│   └── mysql-connector-j-8.2.0.jar           # MySQL JDBC driver
│
└── README.md                                  # This file

```

---

## ✨ Features
### Core Functionality
- ➕ **Add Employee** - Insert new employee records with validation
- 📋 **View All Employees** - Display complete employee list with formatting
- 🔍 **Search by ID** - Find specific employee by unique identifier
- 🏢 **Search by Department** - Filter employees by department
- ✏️ **Update Employee** - Modify existing employee information
- 🗑️ **Delete Employee** - Remove employee records with confirmation
- 📊 **Statistics** - View database analytics (count, salary statistics)

---

## Technical Highlights

- *PreparedStatement* usage to prevent SQL injection
- *ResultSet* handling with proper resource management
- *Connection* management with try-with-resources
- *Exception handling* with user-friendly error messages
- *Input validation* for data integrity
- *Professional UI* with formatted console output

  ---

##  🚀 Setup Instructions
### Prerequisites

- Java JDK 17 or higher
- MySQL Server 8.0 or higher
- VS Code with Java Extension Pack

### Step 1: Install MySQL

- Download MySQL from mysql.com
- Install and start MySQL server
- Note your root password

### Step 2: Create Database

Open MySQL command line or MySQL Workbench and execute:
```bash
sqlCREATE DATABASE employee_db;
USE employee_db;
```
The application will automatically create the employees table on first run.

### Step 3: Download MySQL JDBC Driver

- Download MySQL Connector/J from MySQL Downloads
- Extract the JAR file (e.g., mysql-connector-j-8.2.0.jar)
- Create a lib folder in your project root
- Place the JAR file in the lib folder

### Step 4: Configure Database Connection

 - Open src/com/employeedb/config/DatabaseConfig.java and update:
 - javaprivate static final String PASSWORD = "your_mysql_password";
 - Replace "your_mysql_password" with your actual MySQL root password.


### Step 5: Compile and Run
 - Option A: Using VS Code

   - Open the project folder in VS Code
   - Navigate to EmployeeManagementApp.java
   - Click "Run" button or press F5

- Option B: Using Command Line

```bash
bash# Compile
javac -cp "lib/*" -d bin src/com/employeeDB/EmployeeMain.java src/com/employeeDB/config/DBConfig.java src/com/employeeDB/dao/EmployeeDao.java src/com/employeeDB/model/Employee.java
```

### Run

```bash
java -cp "bin;lib/*" com.employeeDB.EmployeeMain  

```


###💡 Usage Examples

- Adding an Employee
```bash
First Name: John
Last Name: Doe
Email: john.doe@company.com
Department: Engineering
Salary: 75000
Hire Date (yyyy-MM-dd): 2024-01-15
```
- Searching by Department
```bash  
Enter Department: Engineering
```
- Updating an Employee
```bash 
Enter Employee ID to update: 1
[Shows current details]
Enter new details (press Enter to keep current value):
First Name [John]: 
Last Name [Doe]: 
Salary [75000]: 80000
```

### 📊 Database Schema
```bash 
sqlCREATE TABLE employees (
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    department VARCHAR(50) NOT NULL,
    salary DECIMAL(10, 2) NOT NULL,
    hire_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### 🌟 Best Practices Demonstrated

 - **Separation of Concerns:** Model, DAO, and UI are separated
 - **DRY Principle:** Reusable methods for common operations
 - **Error Handling:** Comprehensive try-catch blocks with user-friendly messages
 - **Code Documentation:** JavaDoc comments for all public methods
 - **Resource Management:** Proper closing of database resources
 - **Input Validation:** All user inputs are validated
 - **Professional UI:** Clean, formatted console interface with icons


### 👨‍💻 Author
Developed By **UJJWAL SINGH**

 - Demonstrates JDBC proficiency
 - Follows industry best practices
 - Clean, maintainable code
 - Professional documentation

### 📄 License

 This project is created for educational.

> Note:
> This project demonstrates fundamental JDBC concepts and is designed to showcase practical database programming skills. For production applications, consider using ORM frameworks like Hibernate or Spring Data JPA.
