# Design Pattern Implementation

This project demonstrates **9 design patterns**:

1. **Factory Pattern** — `LoanTypeFactory` chooses the correct interest strategy from loan type.
2. **Strategy Pattern** — `InterestStrategy` allows Simple Interest and Reducing Balance calculations to vary independently.
3. **State Pattern** — `PendingState`, `ActiveState`, `CompletedState`, and `RejectedState` represent loan lifecycle states.
4. **Observer Pattern** — `LoanService` notifies `NotificationObserver` when loan/payment events occur.
5. **Builder Pattern** — `LoanBuilder` creates immutable `Loan` records with readable chained configuration and validation.
6. **Facade Pattern** — `LoanManagementFacade` exposes a simplified API combining loan and reporting services.
7. **Command Pattern** — approval, rejection, and payment actions are encapsulated as command objects.
8. **Adapter Pattern** — `SmsNotificationAdapter` adapts the legacy SMS sender to the application's notification channel interface.
9. **Template Method Pattern** — `ReportTemplate` defines the report-generation algorithm while `LoanSummaryReport` supplies the report body.

## Flow

`UI -> Facade -> Services -> Database`

`LoanTypeFactory -> InterestStrategy`

`LoanService -> Observer -> Notification`

`UI Action -> Command -> LoanService`

`Legacy SMS -> Adapter -> NotificationChannel`

`ReportTemplate -> Concrete Report`
