\c resto_db

-- DISH INSERTION
INSERT INTO dish(name, unit_price) VALUES
    ('Hot dog', 15000.0);

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
    ('1', '4', 1.0);

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