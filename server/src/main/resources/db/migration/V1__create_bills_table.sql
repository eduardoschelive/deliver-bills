-- V1__Create_bills_table.sql

CREATE TABLE bills
(
    uuid            UUID PRIMARY KEY,
    name            TEXT           NOT NULL,
    original_value  DECIMAL(19, 4) NOT NULL,
    corrected_value DECIMAL(19, 4) NOT NULL,
    days_overdue    INT            NOT NULL,
    due_date        DATE           NOT NULL,
    payment_date    DATE           NOT NULL
);
