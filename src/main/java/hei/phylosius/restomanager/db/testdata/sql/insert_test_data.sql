\c resto_db

-- DISH INSERTION
INSERT INTO dish(id, name, unit_price) VALUES
    ('1', 'Hot dog', 15000.0),
    ('-1', 'Omelette', 2000.0);

-- INGREDIENT INSERTION
INSERT INTO ingredient(id, name, modification_date, unit) VALUES
    ('1', 'Saucisse', '2025-01-01 00:00', 'G'),
    ('2', 'Huile', '2025-01-01 00:00', 'L'),
    ('3', 'Oeuf', '2025-01-01 00:00', 'U'),
    ('4', 'Pain', '2025-01-01 00:00', 'U');

-- INGREDIENT PRICE INSERTION
INSERT INTO ingredient_price(ingredient_id, unit_price, date) VALUES
    ('1', 20.0, '2025-01-01 00:00'),
    ('2', 10000.0, '2025-01-01 00:00'),
    ('3', 1000.0, '2025-01-01 00:00'),
    ('4', 1000.0, '2025-01-01 00:00');

-- MAKE UP
INSERT INTO make_up(dish_id, ingredient_id, ingredient_quantity) VALUES
    ('1', '1', 100.0),
    ('1', '2', 0.15),
    ('1', '3', 1.0),
    ('1', '4', 1.0),
    ('-1', '2', 0.05),
    ('-1', '3', 1.0);

-- MOVEMENT_STOCK
INSERT INTO stock_movement(ingredient_id, type, quantity, date) VALUES
    -- IN
    ('3', 'IN', 100.0, '2025-02-01 08:00'),
    ('4', 'IN', 50.0, '2025-02-01 08:00'),
    ('1', 'IN', 10000.0, '2025-02-01 08:00'),
    ('2', 'IN', 20.0, '2025-02-01 08:00'),
    -- OUT
    ('3', 'OUT', 10.0, '2025-02-02 10:00'),
    ('3', 'OUT', 10.0, '2025-02-03 15:00'),
    ('4', 'OUT', 20.0, '2025-02-04 16:00');

-- ORDER
INSERT INTO "order"(id, reference, created_at) VALUES
    ('-1', 'ORDER-1',  '2025-01-02 19:32'),
    ('-2', 'ORDER-2','2025-01-02 19:38');

-- DISH_ORDER
INSERT INTO dish_order(id, dish_id, order_id, quantity) VALUES
    -- FOR '-1' order
    ('-11', '1', '-1', 4),
    ('-101', '-1', '-1', 2),
    -- FOR '-2' order
    ('-21', '1', '-2', 1),
    ('-201', '-1', '-2', 2);

-- DISH_ORDER_STATUS_HISTORY
INSERT INTO dish_order_status_history(dish_order_id, date, status_id) VALUES
    -- FOR '-1' order
    ('-11', '2025-01-02 19:35', 'CONFIRMED'),
    ('-11', '2025-01-02 19:33', 'CREATED'),
    ('-11', '2025-01-02 19:37', 'IN_PREPARATION'),
    ('-11', '2025-01-02 19:40', 'FINISHED'),
    ('-11', '2025-01-02 19:42', 'SERVED'),

    ('-101', '2025-01-02 19:34', 'CREATED'),
    ('-101', '2025-01-02 19:35', 'CONFIRMED'),
    ('-101', '2025-01-02 19:38', 'IN_PREPARATION'),
    ('-101', '2025-01-02 19:40', 'FINISHED'),
    ('-101', '2025-01-02 19:42', 'SERVED'),

    -- FOR '-2' order
    ('-21', '2025-01-02 19:39', 'CREATED'),
    ('-21', '2025-01-02 19:40', 'CONFIRMED'),
    ('-21', '2025-01-02 19:41', 'IN_PREPARATION'),
    ('-21', '2025-01-02 19:45', 'FINISHED'),
    ('-21', '2025-01-02 19:47', 'SERVED'),

    ('-201', '2025-01-02 19:39', 'CREATED'),
    ('-201', '2025-01-02 19:40', 'CONFIRMED'),
    ('-201', '2025-01-02 19:41', 'IN_PREPARATION'),
    ('-201', '2025-01-02 19:43', 'FINISHED'),
    ('-201', '2025-01-02 19:45', 'SERVED');