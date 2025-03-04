-- Create Customers Table
CREATE TABLE customers (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    is_admin BOOLEAN NOT NULL
);

-- Insert Customers
INSERT INTO customers (id, name, password, email, phone_number, is_admin) VALUES
('1', 'admin', 'password', 'admin@example.com', '1234567890', true),
('2', 'user1', 'password1', 'user1@example.com', '0987654321', false),
('3', 'user2', 'password2', 'user2@example.com', '1122334455', false);

-- Create Assets Table
CREATE TABLE assets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id VARCHAR(255) NOT NULL,
    asset_name VARCHAR(255) NOT NULL,
    total_size DOUBLE NOT NULL,
    usable_size DOUBLE NOT NULL,
    size INT DEFAULT 0 NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);

-- Insert Assets
INSERT INTO assets (customer_id, asset_name, total_size, usable_size, size) VALUES
('1', 'TRY', 10000, 10000, 100),
('2', 'TRY', 5000, 5000, 50),
('3', 'TRY', 2000, 2000, 20);

-- Create Orders Table
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    asset_name VARCHAR(255) NOT NULL,
    order_side VARCHAR(255) NOT NULL,
    size INT NOT NULL,
    price DOUBLE NOT NULL,
    status VARCHAR(255) NOT NULL,
    create_date TIMESTAMP NOT NULL,
    customer_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);

-- Insert Orders
INSERT INTO orders (asset_name, order_side, size, price, status, create_date, customer_id) VALUES
('TRY', 'BUY', 10, 150.0, 'PENDING', '2025-01-01 00:00:00', '1'),
('TRY', 'SELL', 5, 100.0, 'PENDING', '2025-01-02 00:00:00', '2'),
('TRY', 'BUY', 20, 200.0, 'PENDING', '2025-01-03 00:00:00', '3');