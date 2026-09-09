package com.loanms;

import com.loanms.db.Database;
import com.loanms.db.Seeder;
import com.loanms.ui.MainView;

import javafx.application.Application;
import javafx.stage.Stage;


public class LoanApplication extends Application {


    @Override
    public void start(Stage stage) {

        // Initialize database
        Database.initialize();


        // Add sample data
        Seeder.seed();


        // Set application title
        stage.setTitle(
                "Loan Management System"
        );


        // Create and set main scene
        stage.setScene(
                new MainView().createScene()
        );


        // Show application window
        stage.show();
    }


    public static void main(String[] args) {

        launch(args);
    }
}