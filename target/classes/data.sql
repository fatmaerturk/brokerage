-- Create customers table
CREATE TABLE IF NOT EXISTS customers (
    customer_id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    password VARCHAR(255)
);

-- Insert initial data into customers table
INSERT INTO customers (customer_id, name, password) VALUES ('fatma', 'Fatma Erturk', 'password');
INSERT INTO customers (customer_id, name, password) VALUES ('customer1', 'Jane Doe', 'password');

-- Create assets table
CREATE TABLE IF NOT EXISTS assets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id VARCHAR(255),
    asset_name VARCHAR(255),
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

-- Insert initial data into assets table
INSERT INTO assets (customer_id, asset_name) VALUES ('fatma', 'AAPL');
INSERT INTO assets (customer_id, asset_name) VALUES ('customer1', 'GOOGL');
INSERT INTO assets (customer_id, asset_name) VALUES ('customer1', 'MSFT');

-- Create orders table
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id VARCHAR(255),
    asset_name VARCHAR(255),
    order_side VARCHAR(255),
    size INT,
    price DOUBLE,
    CONSTRAINT fk_customer_order FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

-- Insert initial data into orders table
INSERT INTO orders (customer_id, asset_name, order_side, size, price)
VALUES ('fatma', 'AAPL', 'BUY', 10, 150.0);
INSERT INTO orders (customer_id, asset_name, order_side, size, price)
VALUES ('customer1', 'MSFT', 'SELL', 5, 200.0);