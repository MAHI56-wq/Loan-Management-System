package com.loanms.patterns.observer;
import com.loanms.db.Database;
import java.sql.*;
import java.time.LocalDateTime;
public class NotificationObserver implements LoanObserver {
    public void onLoanChanged(int loanId, String message) {
        try (Connection c=Database.connect();
             PreparedStatement p=c.prepareStatement("INSERT INTO notification(loan_id,message,created_at) VALUES(?,?,?)")) {
            p.setInt(1, loanId); p.setString(2,message); p.setString(3,LocalDateTime.now().toString()); p.executeUpdate();
        } catch(SQLException e) { throw new IllegalStateException("Notification save failed",e); }
    }
}