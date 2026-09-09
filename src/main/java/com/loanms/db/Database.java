package com.loanms.db;

import java.sql.*;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Database {
    private static final String URL = "jdbc:sqlite:loan-management.db";
    private Database() {}

    public static Connection connect() throws SQLException {
        Connection c = DriverManager.getConnection(URL);
        try (Statement s = c.createStatement()) {
            s.execute("PRAGMA foreign_keys = ON");
        }
        return c;
    }

    public static void initialize() {
        try (Connection c = connect();
             Statement s = c.createStatement()) {
            String schema = Files.readString(Path.of("src/main/resources/schema.sql"));
            for (String sql : schema.split(";")) {
                if (!sql.isBlank()) s.execute(sql);
            }
        } catch (Exception e) {
            throw new IllegalStateException("Database initialization failed", e);
        }
    }
}
