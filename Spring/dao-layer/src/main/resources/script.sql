SELECT *
FROM orders
INNER JOIN customers ON orders.customer_id = customers.id
WHERE customers.name LIKE :name ;