package com.loanms.db;

import java.sql.Connection;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Seeder {
    private Seeder() {}
    public static void seed() {
        try (Connection c = Database.connect()) {
            String seed = Files.readString(Path.of("src/main/resources/seed.sql"));
            for (String sql : seed.split(";")) {
                if (!sql.isBlank()) c.createStatement().execute(sql);
            }
        } catch (Exception e) {
            throw new IllegalStateException("Database seeding failed", e);
        }
    }
}
