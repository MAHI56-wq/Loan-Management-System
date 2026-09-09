package com.loanms.ui;

import com.loanms.model.Customer;
import com.loanms.model.Loan;
import com.loanms.model.Officer;

import com.loanms.patterns.command.ApproveLoanCommand;
import com.loanms.patterns.command.LoanCommand;
import com.loanms.patterns.command.RecordPaymentCommand;
import com.loanms.patterns.command.RejectLoanCommand;

import com.loanms.patterns.facade.LoanManagementFacade;

import com.loanms.service.CustomerService;
import com.loanms.service.LoanService;
import com.loanms.service.OfficerService;
import com.loanms.service.ReportService;

import javafx.beans.property.SimpleStringProperty;

import javafx.collections.FXCollections;

import javafx.geometry.Insets;

import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class MainView {

    private final LoanManagementFacade facade =
            new LoanManagementFacade();

    private final CustomerService customers =
            new CustomerService();

    private final OfficerService officers =
            new OfficerService();

    private final LoanService loans =
            new LoanService();

    private final ReportService reports =
            new ReportService();


    // =====================================================
    // CREATE MAIN SCENE
    // =====================================================

    public Scene createScene() {

        TabPane tabs = new TabPane();

        tabs.getTabs().add(
                dashboardTab()
        );

        tabs.getTabs().add(
                customerTab()
        );

        tabs.getTabs().add(
                officerTab()
        );

        tabs.getTabs().add(
                loanTab()
        );


        BorderPane root =
                new BorderPane();

        root.setCenter(
                tabs
        );

        root.setPadding(
                new Insets(12)
        );


        return new Scene(
                root,
                1100,
                700
        );
    }


    // =====================================================
    // DASHBOARD TAB
    // =====================================================

    private Tab dashboardTab() {

        Button refreshButton =
                new Button(
                        "Refresh Dashboard"
                );


        GridPane grid =
                new GridPane();

        grid.setHgap(20);

        grid.setVgap(12);


        Runnable loadDashboard = () -> {

            grid.getChildren().clear();

            int index = 0;


            for (var entry :
                    reports.summary().entrySet()) {

                String text =
                        entry.getKey()
                                + ": "
                                + String.format(
                                "%.2f",
                                entry.getValue()
                        );


                Label label =
                        new Label(
                                text
                        );


                grid.add(
                        label,
                        index % 2,
                        index / 2
                );


                index++;
            }
        };


        refreshButton.setOnAction(
                event -> loadDashboard.run()
        );


        loadDashboard.run();


        VBox layout =
                new VBox(
                        15
                );

        layout.setPadding(
                new Insets(10)
        );

        layout.getChildren().add(
                refreshButton
        );

        layout.getChildren().add(
                grid
        );


        return new Tab(
                "Dashboard",
                layout
        );
    }


    // =====================================================
    // CUSTOMER TAB
    // =====================================================

    private Tab customerTab() {

        TableView<Customer> table =
                new TableView<>();


        TableColumn<Customer, String> nameColumn =
                col(
                        "Name",
                        customer -> customer.name()
                );


        TableColumn<Customer, String> phoneColumn =
                col(
                        "Phone",
                        customer -> customer.phone()
                );


        TableColumn<Customer, String> emailColumn =
                col(
                        "Email",
                        customer -> customer.email()
                );


        // One by one add korar karone
        // generic array warning ashbe na

        table.getColumns().add(
                nameColumn
        );

        table.getColumns().add(
                phoneColumn
        );

        table.getColumns().add(
                emailColumn
        );


        Button addButton =
                new Button(
                        "Add"
                );

        Button updateButton =
                new Button(
                        "Update"
                );

        Button deleteButton =
                new Button(
                        "Delete"
                );


        // ADD CUSTOMER

        addButton.setOnAction(
                event -> customerDialog(
                        table,
                        null
                )
        );


        // UPDATE CUSTOMER

        updateButton.setOnAction(
                event -> {

                    Customer selectedCustomer =
                            table
                                    .getSelectionModel()
                                    .getSelectedItem();


                    if (selectedCustomer == null) {

                        alert(
                                "Please select a customer first."
                        );

                        return;
                    }


                    customerDialog(
                            table,
                            selectedCustomer
                    );
                }
        );


        // DELETE CUSTOMER

        deleteButton.setOnAction(
                event -> {

                    Customer selectedCustomer =
                            table
                                    .getSelectionModel()
                                    .getSelectedItem();


                    if (selectedCustomer == null) {

                        alert(
                                "Please select a customer first."
                        );

                        return;
                    }


                    try {

                        customers.delete(
                                selectedCustomer.id()
                        );


                        refresh(
                                table,
                                customers.list()
                        );

                    } catch (Exception exception) {

                        alert(
                                exception.getMessage()
                        );
                    }
                }
        );


        refresh(
                table,
                customers.list()
        );


        HBox buttonBox =
                new HBox(
                        8
                );

        buttonBox.getChildren().add(
                addButton
        );

        buttonBox.getChildren().add(
                updateButton
        );

        buttonBox.getChildren().add(
                deleteButton
        );


        VBox layout =
                new VBox(
                        10
                );

        layout.setPadding(
                new Insets(10)
        );

        layout.getChildren().add(
                buttonBox
        );

        layout.getChildren().add(
                table
        );


        return new Tab(
                "Customers",
                layout
        );
    }


    // =====================================================
    // CUSTOMER DIALOG
    // =====================================================

    private void customerDialog(
            TableView<Customer> table,
            Customer oldCustomer
    ) {

        String defaultValue = "";


        if (oldCustomer != null) {

            defaultValue =
                    oldCustomer.name()
                            + " | "
                            + oldCustomer.phone()
                            + " | "
                            + oldCustomer.email();
        }


        TextInputDialog dialog =
                new TextInputDialog(
                        defaultValue
                );


        dialog.setHeaderText(
                "Enter: Name | Phone | Email"
        );


        dialog.showAndWait().ifPresent(
                value -> {

                    try {

                        String[] data =
                                value.split(
                                        "\\|",
                                        -1
                                );


                        if (data.length < 2) {

                            throw new IllegalArgumentException(
                                    "Please enter: Name | Phone | Email"
                            );
                        }


                        String name =
                                data[0].trim();

                        String phone =
                                data[1].trim();

                        String email =
                                data.length > 2
                                        ? data[2].trim()
                                        : "";


                        if (oldCustomer == null) {

                            customers.add(
                                    name,
                                    phone,
                                    email
                            );

                        } else {

                            customers.update(
                                    oldCustomer.id(),
                                    name,
                                    phone,
                                    email
                            );
                        }


                        refresh(
                                table,
                                customers.list()
                        );

                    } catch (Exception exception) {

                        alert(
                                exception.getMessage()
                        );
                    }
                }
        );
    }


    // =====================================================
    // OFFICER TAB
    // =====================================================

    private Tab officerTab() {

        TableView<Officer> table =
                new TableView<>();


        TableColumn<Officer, String> nameColumn =
                col(
                        "Name",
                        officer -> officer.name()
                );


        TableColumn<Officer, String> phoneColumn =
                col(
                        "Phone",
                        officer -> officer.phone()
                );


        TableColumn<Officer, String> emailColumn =
                col(
                        "Email",
                        officer -> officer.email()
                );


        table.getColumns().add(
                nameColumn
        );

        table.getColumns().add(
                phoneColumn
        );

        table.getColumns().add(
                emailColumn
        );


        Button addButton =
                new Button(
                        "Add"
                );

        Button updateButton =
                new Button(
                        "Update"
                );

        Button deleteButton =
                new Button(
                        "Delete"
                );


        // ADD OFFICER

        addButton.setOnAction(
                event -> officerDialog(
                        table,
                        null
                )
        );


        // UPDATE OFFICER

        updateButton.setOnAction(
                event -> {

                    Officer selectedOfficer =
                            table
                                    .getSelectionModel()
                                    .getSelectedItem();


                    if (selectedOfficer == null) {

                        alert(
                                "Please select an officer first."
                        );

                        return;
                    }


                    officerDialog(
                            table,
                            selectedOfficer
                    );
                }
        );


        // DELETE OFFICER

        deleteButton.setOnAction(
                event -> {

                    Officer selectedOfficer =
                            table
                                    .getSelectionModel()
                                    .getSelectedItem();


                    if (selectedOfficer == null) {

                        alert(
                                "Please select an officer first."
                        );

                        return;
                    }


                    try {

                        officers.delete(
                                selectedOfficer.id()
                        );


                        refresh(
                                table,
                                officers.list()
                        );

                    } catch (Exception exception) {

                        alert(
                                exception.getMessage()
                        );
                    }
                }
        );


        refresh(
                table,
                officers.list()
        );


        HBox buttonBox =
                new HBox(
                        8
                );

        buttonBox.getChildren().add(
                addButton
        );

        buttonBox.getChildren().add(
                updateButton
        );

        buttonBox.getChildren().add(
                deleteButton
        );


        VBox layout =
                new VBox(
                        10
                );

        layout.setPadding(
                new Insets(10)
        );

        layout.getChildren().add(
                buttonBox
        );

        layout.getChildren().add(
                table
        );


        return new Tab(
                "Officers",
                layout
        );
    }


    // =====================================================
    // OFFICER DIALOG
    // =====================================================

    private void officerDialog(
            TableView<Officer> table,
            Officer oldOfficer
    ) {

        String defaultValue = "";


        if (oldOfficer != null) {

            defaultValue =
                    oldOfficer.name()
                            + " | "
                            + oldOfficer.phone()
                            + " | "
                            + oldOfficer.email();
        }


        TextInputDialog dialog =
                new TextInputDialog(
                        defaultValue
                );


        dialog.setHeaderText(
                "Enter: Name | Phone | Email"
        );


        dialog.showAndWait().ifPresent(
                value -> {

                    try {

                        String[] data =
                                value.split(
                                        "\\|",
                                        -1
                                );


                        if (data.length < 2) {

                            throw new IllegalArgumentException(
                                    "Please enter: Name | Phone | Email"
                            );
                        }


                        String name =
                                data[0].trim();

                        String phone =
                                data[1].trim();

                        String email =
                                data.length > 2
                                        ? data[2].trim()
                                        : "";


                        if (oldOfficer == null) {

                            officers.add(
                                    name,
                                    phone,
                                    email
                            );

                        } else {

                            officers.update(
                                    oldOfficer.id(),
                                    name,
                                    phone,
                                    email
                            );
                        }


                        refresh(
                                table,
                                officers.list()
                        );

                    } catch (Exception exception) {

                        alert(
                                exception.getMessage()
                        );
                    }
                }
        );
    }


    // =====================================================
    // LOAN TAB
    // =====================================================

    private Tab loanTab() {

        TableView<Loan> table =
                new TableView<>();


        TableColumn<Loan, String> idColumn =
                col(
                        "ID",
                        loan ->
                                String.valueOf(
                                        loan.id()
                                )
                );


        TableColumn<Loan, String> customerColumn =
                col(
                        "Customer",
                        loan ->
                                String.valueOf(
                                        loan.customerId()
                                )
                );


        TableColumn<Loan, String> officerColumn =
                col(
                        "Officer",
                        loan -> {

                            if (loan.officerId() == null) {

                                return "-";
                            }

                            return String.valueOf(
                                    loan.officerId()
                            );
                        }
                );


        TableColumn<Loan, String> typeColumn =
                col(
                        "Type",
                        loan -> loan.loanType()
                );


        TableColumn<Loan, String> principalColumn =
                col(
                        "Principal",
                        loan ->
                                String.format(
                                        "%.2f",
                                        loan.principal()
                                )
                );


        TableColumn<Loan, String> statusColumn =
                col(
                        "Status",
                        loan -> loan.status()
                );


        table.getColumns().add(
                idColumn
        );

        table.getColumns().add(
                customerColumn
        );

        table.getColumns().add(
                officerColumn
        );

        table.getColumns().add(
                typeColumn
        );

        table.getColumns().add(
                principalColumn
        );

        table.getColumns().add(
                statusColumn
        );


        TextField searchField =
                new TextField();

        searchField.setPromptText(
                "Search ID, customer, type or status"
        );


        Button applyButton =
                new Button(
                        "Apply Loan"
                );

        Button approveButton =
                new Button(
                        "Approve"
                );

        Button rejectButton =
                new Button(
                        "Reject"
                );

        Button assignButton =
                new Button(
                        "Assign Officer"
                );

        Button paymentButton =
                new Button(
                        "Record Payment"
                );

        Button historyButton =
                new Button(
                        "Payment History"
                );


        // =============================================
        // APPLY LOAN
        // =============================================

        applyButton.setOnAction(
                event -> {

                    TextInputDialog dialog =
                            new TextInputDialog();


                    dialog.setHeaderText(
                            "Customer ID | Type | Principal | Rate | Months"
                    );


                    dialog.showAndWait().ifPresent(
                            value -> {

                                try {

                                    String[] data =
                                            value.split(
                                                    "\\|"
                                            );


                                    if (data.length != 5) {

                                        throw new IllegalArgumentException(
                                                "Enter: Customer ID | Type | Principal | Rate | Months"
                                        );
                                    }


                                    int customerId =
                                            Integer.parseInt(
                                                    data[0].trim()
                                            );


                                    String loanType =
                                            data[1].trim();


                                    double principal =
                                            Double.parseDouble(
                                                    data[2].trim()
                                            );


                                    double rate =
                                            Double.parseDouble(
                                                    data[3].trim()
                                            );


                                    int months =
                                            Integer.parseInt(
                                                    data[4].trim()
                                            );


                                    facade.apply(
                                            customerId,
                                            loanType,
                                            principal,
                                            rate,
                                            months
                                    );


                                    refresh(
                                            table,
                                            loans.list()
                                    );

                                } catch (Exception exception) {

                                    alert(
                                            exception.getMessage()
                                    );
                                }
                            }
                    );
                }
        );


        // =============================================
        // APPROVE LOAN
        // =============================================

        approveButton.setOnAction(
                event -> act(
                        table,
                        true
                )
        );


        // =============================================
        // REJECT LOAN
        // =============================================

        rejectButton.setOnAction(
                event -> act(
                        table,
                        false
                )
        );


        // =============================================
        // ASSIGN OFFICER
        // =============================================

        assignButton.setOnAction(
                event -> {

                    Loan selectedLoan =
                            table
                                    .getSelectionModel()
                                    .getSelectedItem();


                    if (selectedLoan == null) {

                        alert(
                                "Please select a loan first."
                        );

                        return;
                    }


                    TextInputDialog dialog =
                            new TextInputDialog();


                    dialog.setHeaderText(
                            "Enter Officer ID"
                    );


                    dialog.showAndWait().ifPresent(
                            value -> {

                                try {

                                    int officerId =
                                            Integer.parseInt(
                                                    value.trim()
                                            );


                                    loans.assignOfficer(
                                            selectedLoan.id(),
                                            officerId
                                    );


                                    refresh(
                                            table,
                                            loans.list()
                                    );

                                } catch (Exception exception) {

                                    alert(
                                            exception.getMessage()
                                    );
                                }
                            }
                    );
                }
        );


        // =============================================
        // RECORD PAYMENT
        // =============================================

        paymentButton.setOnAction(
                event -> {

                    Loan selectedLoan =
                            table
                                    .getSelectionModel()
                                    .getSelectedItem();


                    if (selectedLoan == null) {

                        alert(
                                "Please select a loan first."
                        );

                        return;
                    }


                    TextInputDialog dialog =
                            new TextInputDialog();


                    dialog.setHeaderText(
                            "Enter Payment Amount"
                    );


                    dialog.showAndWait().ifPresent(
                            value -> {

                                try {

                                    double amount =
                                            Double.parseDouble(
                                                    value.trim()
                                            );


                                    LoanCommand command =
                                            new RecordPaymentCommand(
                                                    loans,
                                                    selectedLoan.id(),
                                                    amount
                                            );


                                    command.execute();


                                    refresh(
                                            table,
                                            loans.list()
                                    );

                                } catch (Exception exception) {

                                    alert(
                                            exception.getMessage()
                                    );
                                }
                            }
                    );
                }
        );


        // =============================================
        // PAYMENT HISTORY
        // =============================================

        historyButton.setOnAction(
                event -> {

                    Loan selectedLoan =
                            table
                                    .getSelectionModel()
                                    .getSelectedItem();


                    if (selectedLoan == null) {

                        alert(
                                "Please select a loan first."
                        );

                        return;
                    }


                    String history =
                            String.join(
                                    "\n",
                                    reports.paymentHistory(
                                            selectedLoan.id()
                                    )
                            );


                    if (history.isBlank()) {

                        history =
                                "No payment history found.";
                    }


                    Alert infoAlert =
                            new Alert(
                                    Alert.AlertType.INFORMATION,
                                    history,
                                    ButtonType.OK
                            );


                    infoAlert.setHeaderText(
                            "Payment History"
                    );


                    infoAlert.showAndWait();
                }
        );


        // =============================================
        // SEARCH
        // =============================================

        searchField.textProperty().addListener(
                (observable, oldValue, newValue) -> {

                    try {

                        refresh(
                                table,
                                facade.search(
                                        newValue
                                )
                        );

                    } catch (Exception exception) {

                        alert(
                                exception.getMessage()
                        );
                    }
                }
        );


        refresh(
                table,
                loans.list()
        );


        HBox buttonBox =
                new HBox(
                        8
                );


        buttonBox.getChildren().add(
                applyButton
        );

        buttonBox.getChildren().add(
                approveButton
        );

        buttonBox.getChildren().add(
                rejectButton
        );

        buttonBox.getChildren().add(
                assignButton
        );

        buttonBox.getChildren().add(
                paymentButton
        );

        buttonBox.getChildren().add(
                historyButton
        );


        VBox layout =
                new VBox(
                        10
                );

        layout.setPadding(
                new Insets(10)
        );


        layout.getChildren().add(
                searchField
        );

        layout.getChildren().add(
                buttonBox
        );

        layout.getChildren().add(
                table
        );


        return new Tab(
                "Loans",
                layout
        );
    }


    // =====================================================
    // APPROVE OR REJECT LOAN
    // =====================================================

    private void act(
            TableView<Loan> table,
            boolean approve
    ) {

        Loan selectedLoan =
                table
                        .getSelectionModel()
                        .getSelectedItem();


        if (selectedLoan == null) {

            alert(
                    "Please select a loan first."
            );

            return;
        }


        try {

            LoanCommand command;


            if (approve) {

                command =
                        new ApproveLoanCommand(
                                loans,
                                selectedLoan.id()
                        );

            } else {

                command =
                        new RejectLoanCommand(
                                loans,
                                selectedLoan.id()
                        );
            }


            command.execute();


            refresh(
                    table,
                    loans.list()
            );

        } catch (Exception exception) {

            alert(
                    exception.getMessage()
            );
        }
    }


    // =====================================================
    // CREATE TABLE COLUMN
    // =====================================================

    private <T> TableColumn<T, String> col(
            String title,
            java.util.function.Function<T, String> function
    ) {

        TableColumn<T, String> column =
                new TableColumn<>(
                        title
                );


        column.setCellValueFactory(
                cellData ->

                        new SimpleStringProperty(
                                function.apply(
                                        cellData.getValue()
                                )
                        )
        );


        return column;
    }


    // =====================================================
    // REFRESH TABLE
    // =====================================================

    private <T> void refresh(
            TableView<T> table,
            java.util.List<T> data
    ) {

        table.setItems(
                FXCollections.observableArrayList(
                        data
                )
        );
    }


    // =====================================================
    // ERROR ALERT
    // =====================================================

    private void alert(
            String message
    ) {

        String errorMessage =
                message == null || message.isBlank()
                        ? "Operation failed."
                        : message;


        Alert errorAlert =
                new Alert(
                        Alert.AlertType.ERROR,
                        errorMessage,
                        ButtonType.OK
                );


        errorAlert.setHeaderText(
                "Error"
        );


        errorAlert.showAndWait();
    }
}
