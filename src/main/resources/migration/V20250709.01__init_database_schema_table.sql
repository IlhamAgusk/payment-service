-- -- 1. Buat database jika belum ada
-- CREATE DATABASE postgre_db;

-- 2. Connect ke database
-- \c postgre_db

-- 3. Buat schema
CREATE SCHEMA payment_schema;

CREATE TABLE payment_schema.payments (
     id UUID PRIMARY KEY,
     amount NUMERIC(15, 2) NOT NULL,
     payment_method VARCHAR(255) NOT NULL,
     status VARCHAR(100) NOT NULL,
     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP,
     created_by VARCHAR(100) NOT NULL DEFAULT 'system',
     updated_by VARCHAR(100) NOT NULL DEFAULT 'system'
);

