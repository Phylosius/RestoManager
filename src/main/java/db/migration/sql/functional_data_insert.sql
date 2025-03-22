-- command_status
INSERT INTO order_status (id, "order", name) VALUES
    ('CREATED', 1, 'CREATED'),
    ('CONFIRMED', 2, 'CONFIRMED'),
    ('IN_PREPARATION', 3, 'IN_PREPARATION'),
    ('FINISHED', 4, 'FINISHED'),
    ('SERVED', 5, 'SERVED');