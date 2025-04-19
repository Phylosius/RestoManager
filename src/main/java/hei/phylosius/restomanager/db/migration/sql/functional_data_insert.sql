-- command_status
INSERT INTO order_status (id, "order", name) VALUES
    ('CREATED', 1, 'CREATED'),
    ('CONFIRMED', 2, 'CONFIRMED'),
    ('IN_PROGRESS', 3, 'IN_PROGRESS'),
    ('FINISHED', 4, 'FINISHED'),
    ('DELIVERED', 5, 'DELIVERED');