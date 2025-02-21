\c resto_db

-- DISH INSERTION
INSERT INTO dish(name, unit_price) VALUES
    ('Hot dog', 15000.0);

-- INGREDIENT INSERTION
INSERT INTO ingredient(name, modification_date, unit_price, unit) VALUES
    ('Saucisse', '2025-01-01 00:00', 20.0, 'G'),
    ('Huile', '2025-01-01 00:00', 10000.0, 'L'),
    ('Oeuf', '2025-01-01 00:00', 1000.0, 'U'),
    ('Pain', '2025-01-01 00:00', 1000.0, 'U');

-- MAKE UP
INSERT INTO make_up(dish_id, ingredient_id, ingredient_quantity) VALUES
    ('1', '1', 100.0),
    ('1', '2', 0.15),
    ('1', '3', 1.0),
    ('1', '4', 1.0);
