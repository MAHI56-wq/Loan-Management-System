# Loan Management System

Course-ready Java 17 + JavaFX + Maven + SQLite starter project.

## Features
- Customer add/list
- Officer add/list
- Loan application/search
- Loan approval/rejection
- Officer assignment
- Payment recording
- Automatic loan completion
- Monthly payment calculation
- Dashboard/report
- Validation and error handling
- SQLite persistent storage
- Database seeder
- Factory + Strategy + State + Observer + Builder + Facade + Command + Adapter + Template Method patterns

## Design Patterns
- **Factory**: selects loan type/interest strategy.
- **Strategy**: interchangeable interest/EMI calculations.
- **State**: controls Pending/Active/Completed/Rejected lifecycle.
- **Observer**: persists loan/payment notifications.
- **Builder**: safely constructs Loan objects with readable chained fields.
- **Facade**: provides a simplified `LoanManagementFacade` API for UI/client code.
- **Command**: encapsulates approve, reject, and payment operations.
- **Adapter**: adapts a legacy SMS notification API to the application notification channel.
- **Template Method**: standardizes report generation while allowing report-specific bodies.

## Run
```bash
mvn clean test
mvn javafx:run
```

The SQLite database is created as `loan-management.db` in the project directory.

## Course customization checklist
Add your final UML/ER diagrams, meaningful Git branches/PRs, team commits, richer CRUD screens and detailed reports before submission.
