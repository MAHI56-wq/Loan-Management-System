# Loan Management System

A desktop-based **Loan Management System** developed using **Java, JavaFX, Maven, SQLite, and JUnit**. The system helps manage customers, loan officers, loan applications, payments, reports, and loan-related workflows efficiently.

## Features

* Customer management
* Loan officer management
* Loan application
* Loan search and filtering
* Loan approval and rejection
* Officer assignment
* Payment recording
* Payment history
* Automatic loan status updates
* Loan completion tracking
* Interest calculation
* Multiple loan calculation strategies
* Loan risk assessment
* Loan eligibility checking
* Dashboard and statistics
* Report generation
* CSV loan data export
* Input validation
* Error handling
* SQLite persistent data storage

## Technologies

* **Java 17+**
* **JavaFX**
* **Maven**
* **SQLite**
* **JDBC**
* **JUnit 5**
* **Git and GitHub**

## Design Patterns

The project uses several design patterns to improve maintainability, flexibility, and extensibility:

* **Command Pattern** – Used for loan approval, rejection, and payment actions
* **Facade Pattern** – Provides a simplified interface for loan-related operations
* **Strategy Pattern** – Supports different interest calculation methods
* **Factory Pattern** – Selects appropriate loan calculation strategies
* **Observer Pattern** – Handles loan events and notifications
* **State Pattern** – Manages loan status and lifecycle transitions
* **Chain of Responsibility Pattern** – Supports multi-level loan approval workflows
* **Builder Pattern** – Helps construct complex loan-related objects

## Main Entities

* Customer
* Officer
* Loan
* Payment
* Notification

## Project Structure

```text
LoanManagementSystem/
│
├── pom.xml
├── README.md
├── .gitignore
│
├── database/
│   ├── schema.sql
│   └── seed.sql
│
├── docs/
│   ├── FEATURES.md
│   ├── PROJECT_DOCUMENTATION.md
│   └── patterns/
│       └── DESIGN_PATTERNS.md
│
└── src/
    │
    ├── main/
    │   │
    │   ├── java/
    │   │   └── com/
    │   │       └── loanms/
    │   │           │
    │   │           ├── LoanApplication.java
    │   │           │
    │   │           ├── db/
    │   │           ├── dto/
    │   │           ├── model/
    │   │           ├── repository/
    │   │           ├── service/
    │   │           ├── feature/
    │   │           ├── util/
    │   │           ├── ui/
    │   │           │
    │   │           └── patterns/
    │   │               ├── adapter/
    │   │               ├── bridge/
    │   │               ├── builder/
    │   │               ├── chain/
    │   │               ├── command/
    │   │               ├── decorator/
    │   │               ├── facade/
    │   │               ├── factory/
    │   │               ├── mediator/
    │   │               ├── observer/
    │   │               ├── prototype/
    │   │               ├── proxy/
    │   │               ├── singleton/
    │   │               ├── state/
    │   │               ├── strategy/
    │   │               └── template/
    │   │
    │   └── resources/
    │       ├── schema.sql
    │       └── seed.sql
    │
    └── test/
        └── java/
            └── com/
                └── loanms/
                    ├── InterestStrategyTest.java
                    ├── feature/
                    └── patterns/
Main Workflows
Loan Application Workflow
Customer
   ↓
Apply for Loan
   ↓
Validate Information
   ↓
Create Loan
   ↓
Calculate Loan Information
   ↓
Pending Approval
Loan Approval Workflow
Loan Request
   ↓
Officer Assignment
   ↓
Approval Process
   ↓
Approved / Rejected
Payment Workflow
Active Loan
   ↓
Record Payment
   ↓
Update Loan Information
   ↓
Check Remaining Amount
   ↓
Completed
Database

The application uses SQLite as its persistent database.

The database stores information about:

Customers
Officers
Loans
Payments
Notifications

The database is initialized when the application starts, and seed data can be used to populate sample records.

How to Run
Requirements

Make sure you have installed:

Java 17 or later
Maven
Check Java
java -version
Check Maven
mvn -version
Run the Project

Clone the repository:

git clone <repository-url>

Go to the project directory:

cd LoanManagementSystem

Compile the project:

mvn clean compile

Run the JavaFX application:

mvn javafx:run

Alternatively:

mvn clean javafx:run
Testing

Run the test suite using:

mvn test

The tests cover important areas such as:

Interest calculation
Loan-related business logic
Feature services
Design pattern implementations
Team Collaboration

The project is maintained using Git and GitHub.

Team members contribute through:

Feature branches
Meaningful commits
Pull requests
Code reviews
Testing and bug fixes
Future Improvements

Possible future improvements include:

User authentication
Role-based access control
Email notifications
SMS notifications
PDF report generation
Advanced analytics
Credit score integration
Customer loan history
Backup and restore functionality
Project Status

Completed / Academic Project

