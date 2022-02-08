CREATE TABLE customers (
    id INTEGER PRIMARY KEY,
    name TEXT,
    surname TEXT,
    age INTEGER,
    phone_number TEXT
);
CREATE UNIQUE INDEX customers_id_unique ON customers(id);

INSERT INTO customers 
("name", "surname", "age", "phone_number")
VALUES 
("Alexey", "Piterson", 45, "+78129028117"),
("Nellie", "Waddle", 25, "+78129021111"),
("Rosemary", "Stevens", 33, "+78129028024");


CREATE TABLE orders (
    id INTEGER PRIMARY KEY,
    date DATE,
    customer_id INTEGER 
        REFERENCES customers(id) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE,
    product_name TEXT,
    amount INTEGER
);
CREATE UNIQUE INDEX orders_id_unique ON orders(id);
CREATE INDEX customer_id_index ON orders(customer_id);

INSERT INTO orders 
("date", "customer_id", "product_name", "amount")
VALUES 
("2021-07-26", 1, "Mock Orange", 24),
("2021-08-16", 2, "Vinca", 55),
("2021-08-17", 1, "Petunias", 11),
("2021-10-26", 3, "Snap Dragon", 10),
("2021-11-03", 1, "Passion Flower", 4),
("2021-12-24", 2, "Poppy", 28);
