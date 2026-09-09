package com.loanms.console;

import com.loanms.feature.AmortizationService;
import com.loanms.feature.PaymentPlan;
import com.loanms.model.Loan;
import com.loanms.patterns.decorator.BaseLoanCost;
import com.loanms.patterns.decorator.LoanCost;
import com.loanms.patterns.decorator.ProcessingFeeDecorator;
import com.loanms.service.AnalyticsService;
import com.loanms.service.LoanService;
import com.loanms.util.MoneyUtil;

import java.util.List;
import java.util.Scanner;

/**
 * Optional console demonstration for users who want to inspect the business
 * features without navigating the JavaFX interface.
 */
public class LoanConsoleDemo {
    private final Scanner scanner = new Scanner(System.in);
    private final LoanService loanService = new LoanService();
    private final AnalyticsService analyticsService = new AnalyticsService();

    public void run() {
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> listLoans();
                    case "2" -> calculateMonthlyPayment();
                    case "3" -> showAmortization();
                    case "4" -> showDashboard();
                    case "5" -> calculateFees();
                    case "0" -> running = false;
                    default -> System.out.println("Please select a valid option.");
                }
            } catch (RuntimeException exception) {
                System.out.println("Error: " + exception.getMessage());
            }

            System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("===============================");
        System.out.println(" Loan Management System Console");
        System.out.println("===============================");
        System.out.println("1. List loans");
        System.out.println("2. Calculate monthly payment");
        System.out.println("3. Show amortization plan");
        System.out.println("4. Show dashboard summary");
        System.out.println("5. Calculate loan fees");
        System.out.println("0. Exit");
        System.out.print("Choice: ");
    }

    private void listLoans() {
        List<Loan> loans = loanService.list();

        if (loans.isEmpty()) {
            System.out.println("No loans found.");
            return;
        }

        for (Loan loan : loans) {
            System.out.println(
                    "#" + loan.id()
                            + " | " + loan.loanType()
                            + " | " + MoneyUtil.format(loan.principal())
                            + " | " + loan.status());
        }
    }

    private void calculateMonthlyPayment() {
        System.out.print("Loan type: ");
        String type = scanner.nextLine();

        System.out.print("Principal: ");
        double principal = Double.parseDouble(scanner.nextLine());

        System.out.print("Annual interest rate: ");
        double rate = Double.parseDouble(scanner.nextLine());

        System.out.print("Term in months: ");
        int months = Integer.parseInt(scanner.nextLine());

        double payment = loanService.monthlyPayment(
                type, principal, rate, months);

        System.out.println("Monthly payment: " + MoneyUtil.format(payment));
    }

    private void showAmortization() {
        System.out.print("Principal: ");
        double principal = Double.parseDouble(scanner.nextLine());

        System.out.print("Annual interest rate: ");
        double rate = Double.parseDouble(scanner.nextLine());

        System.out.print("Term in months: ");
        int months = Integer.parseInt(scanner.nextLine());

        List<PaymentPlan> plans = new AmortizationService()
                .buildPlan(principal, rate, months);

        System.out.println("Month | Payment | Interest | Principal | Balance");

        for (PaymentPlan plan : plans) {
            System.out.printf(
                    "%5d | %7.2f | %8.2f | %9.2f | %7.2f%n",
                    plan.installmentNumber(),
                    plan.payment(),
                    plan.interest(),
                    plan.principalPaid(),
                    plan.closingBalance());
        }
    }

    private void showDashboard() {
        var dashboard = analyticsService.dashboard();

        System.out.println("Total loans: " + dashboard.totalLoans());
        System.out.println("Pending: " + dashboard.pendingLoans());
        System.out.println("Active: " + dashboard.activeLoans());
        System.out.println("Completed: " + dashboard.completedLoans());
        System.out.println("Rejected: " + dashboard.rejectedLoans());
        System.out.println("Total principal: "
                + MoneyUtil.format(dashboard.totalPrincipal()));
        System.out.println("Average principal: "
                + MoneyUtil.format(dashboard.averagePrincipal()));
    }

    private void calculateFees() {
        System.out.print("Principal: ");
        double principal = Double.parseDouble(scanner.nextLine());

        System.out.print("Processing fee: ");
        double fee = Double.parseDouble(scanner.nextLine());

        LoanCost cost = new BaseLoanCost(principal);
        cost = new ProcessingFeeDecorator(cost, fee);

        System.out.println("Description: " + cost.description());
        System.out.println("Total cost: " + MoneyUtil.format(cost.totalCost()));
    }
}
