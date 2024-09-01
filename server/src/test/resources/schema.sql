CREATE TABLE IF NOT EXISTS bills
(
    uuid            UUID PRIMARY KEY,
    name            TEXT           NOT NULL,
    original_value  DECIMAL(19, 4) NOT NULL,
    corrected_value DECIMAL(19, 4) NOT NULL,
    days_overdue    INT            NOT NULL,
    due_date        DATE           NOT NULL,
    payment_date    DATE           NOT NULL
);

CREATE TABLE IF NOT EXISTS filter_test_table
(
    id            SERIAL PRIMARY KEY,
    string_field  TEXT NOT NULL,
    boolean_field BOOLEAN
);