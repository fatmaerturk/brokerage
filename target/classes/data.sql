-- Create customers table
CREATE TABLE IF NOT EXISTS customers (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255),
    phone_number VARCHAR(255)
);

-- Insert initial data into customers table
INSERT INTO customers (id, name, password, email, phone_number) VALUES ('1', 'admin', 'password', 'admin@example.com', '1234567890');

-- Create assets table
CREATE TABLE IF NOT EXISTS assets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id VARCHAR(255),
    asset_name VARCHAR(255),
    size INT,
    usable_size INT,
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customers(id)
);

-- Create orders table
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id VARCHAR(255),
    asset_name VARCHAR(255),
    order_side VARCHAR(255),
    size INT,
    price DOUBLE,
    status VARCHAR(255),
    create_date TIMESTAMP,
    CONSTRAINT fk_customer_order FOREIGN KEY (customer_id) REFERENCES customers(id)
);

-- Insert initial data into assets table
INSERT INTO assets (customer_id, asset_name, size, usable_size) VALUES ('1', 'TRY', 1000, 1000);