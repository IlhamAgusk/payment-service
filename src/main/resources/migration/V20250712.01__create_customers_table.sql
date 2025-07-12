CREATE TABLE payment_schema.customers (
      id VARCHAR(36) PRIMARY KEY,
      name VARCHAR(255) NOT NULL,
      email VARCHAR(255) NOT NULL UNIQUE,
      phone VARCHAR(20),
      created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      created_by VARCHAR(100) NOT NULL DEFAULT 'system',
      updated_by VARCHAR(100) NOT NULL DEFAULT 'system'
);
