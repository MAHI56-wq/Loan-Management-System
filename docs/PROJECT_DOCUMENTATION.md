# Project Documentation

## Architecture
JavaFX UI -> Service Layer -> SQLite Database.

## Entities
Customer, Officer, Loan, Payment, Notification.

## Lifecycle
Pending -> Active -> Completed
Pending -> Rejected

## Patterns
Factory + Strategy for loan-type interest calculation.
State for lifecycle transitions.
Observer for notification persistence.

## Build
`mvn clean test`

## Run
`mvn javafx:run`
