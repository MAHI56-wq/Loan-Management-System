CREATE TABLE IF NOT EXISTS customer (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    phone TEXT NOT NULL UNIQUE,
    email TEXT
);

CREATE TABLE IF NOT EXISTS officer (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    phone TEXT NOT NULL,
    email TEXT
);

CREATE TABLE IF NOT EXISTS loan (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    customer_id INTEGER NOT NULL,
    officer_id INTEGER,
    loan_type TEXT NOT NULL,
    principal REAL NOT NULL CHECK(principal > 0),
    annual_interest_rate REAL NOT NULL CHECK(annual_interest_rate >= 0),
    term_months INTEGER NOT NULL CHECK(term_months > 0),
    status TEXT NOT NULL,
    created_at TEXT NOT NULL,
    FOREIGN KEY(customer_id) REFERENCES customer(id),
    FOREIGN KEY(officer_id) REFERENCES officer(id)
);

CREATE TABLE IF NOT EXISTS payment (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    loan_id INTEGER NOT NULL,
    amount REAL NOT NULL CHECK(amount > 0),
    paid_at TEXT NOT NULL,
    FOREIGN KEY(loan_id) REFERENCES loan(id)
);

CREATE TABLE IF NOT EXISTS notification (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    loan_id INTEGER,
    message TEXT NOT NULL,
    created_at TEXT NOT NULL,
    FOREIGN KEY(loan_id) REFERENCES loan(id)
);
