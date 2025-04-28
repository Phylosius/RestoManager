\c resto_db

-- DISH INSERTION
INSERT INTO dish(id, name, unit_price) VALUES
                                           ('1', 'Hot dog', 15000.0),
                                           ('2', 'Omelette', 5000.0),
                                           ('3', 'Saucisse frit', 3500.0);

-- INGREDIENT INSERTION
INSERT INTO ingredient(id, name, modification_date, unit) VALUES
                                                              ('1', 'Saucisse', '2025-01-01 00:00', 'G'),
                                                              ('2', 'Huile', '2025-01-01 00:00', 'L'),
                                                              ('3', 'Oeuf', '2025-01-01 00:00', 'U'),
                                                              ('4', 'Pain', '2025-01-01 00:00', 'U');

-- MAKE UP
INSERT INTO make_up(dish_id, ingredient_id, ingredient_quantity) VALUES
                                                                     ('1', '1', 100.0),
                                                                     ('1', '2', 0.15),
                                                                     ('1', '3', 1.0),
                                                                     ('1', '4', 1.0),
                                                                     ('2', '2', 0.15),
                                                                     ('2', '3', 3.0),
                                                                     ('3', '2', 0.2),
                                                                     ('3', '1', 200.0);

-- MOVEMENT_STOCK sale point 2
INSERT INTO stock_movement(ingredient_id, type, quantity, date) VALUES
                                                                    -- IN
                                                                    ('3', 'IN', 50.0, '2025-04-01 08:00'),
                                                                    ('4', 'IN', 25.0, '2025-04-01 08:00'),
                                                                    ('1', 'IN', 10000.0, '2025-04-01 08:00'),
                                                                    ('2', 'IN', 10.0, '2025-04-01 08:00');

-- INGREDIENT PRICE sale point 2
INSERT INTO ingredient_price (ingredient_id, unit_price, date) VALUES
                                                                   ('1', 15, '2025-04-01 08:00'),
                                                                   ('2', 12000, '2025-04-01 08:00'),
                                                                   ('3', 800, '2025-04-01 08:00'),
                                                                   ('4', 600, '2025-04-01 08:00'),
                                                                   ('1', 10, '2025-04-15 08:00'),
                                                                   ('2', 15000, '2025-04-15 08:00'),
                                                                   ('3', 1000, '2025-04-15 08:00'),
                                                                   ('4', 500, '2025-04-15 08:00');

-- ORDER sale point 2
INSERT INTO "order"(id, reference, created_at) VALUES
                                                   ('1', 'CMD-001',  '2025-01-03 19:32'),
                                                   ('2', 'CMD-002','2025-01-03 19:38'),
                                                   ('3', 'CMD-003','2025-01-03 19:40'),
                                                   ('4', 'CMD-004','2025-01-03 19:41');

-- DISH_ORDER sale point 2
INSERT INTO dish_order(id, dish_id, order_id, quantity) VALUES
                                                            -- FOR CMD-001
                                                            ('1', '1', '1', 2),
                                                            ('2', '3', '1', 1),
                                                            -- FOR CMD-002
                                                            ('3', '2', '2', 1),
                                                            -- FOR CMD-003
                                                            ('4', '1', '3', 1),
                                                            -- FOR CMD-003
                                                            ('5', '3', '4', 2);

-- DISH_ORDER_STATUS_HISTORY sale point 2
INSERT INTO dish_order_status_history(dish_order_id, date, status_id) VALUES
                                                                          -- FOR CMD-001
                                                                          ('1', '2025-04-19 07:47', 'CONFIRMED'),
                                                                          ('1', '2025-04-19 07:49', 'CREATED'),
                                                                          ('1', '2025-04-19 08:00', 'IN_PROGRESS'),
                                                                          ('1', '2025-04-19 08:06', 'FINISHED'),
                                                                          ('1', '2025-04-19 08:15', 'DELIVERED'),

                                                                          ('1', '2025-04-19 07:47', 'CREATED'),
                                                                          ('1', '2025-04-19 07:49', 'CONFIRMED'),
                                                                          ('1', '2025-04-19 08:00', 'IN_PROGRESS'),
                                                                          ('1', '2025-04-19 08:03', 'FINISHED'),
                                                                          ('1', '2025-04-19 08:10', 'DELIVERED'),

                                                                          -- FOR CMD-002
                                                                          ('2', '2025-04-19 07:50', 'CREATED'),
                                                                          ('2', '2025-04-19 08:20', 'CONFIRMED'),
                                                                          ('2', '2025-04-19 09:00', 'IN_PROGRESS'),
                                                                          ('2', '2025-04-19 09:07', 'FINISHED'),
                                                                          ('2', '2025-04-19 09:12', 'DELIVERED'),

                                                                          -- FOR CMD-003
                                                                          ('3', '2025-04-19 09:50', 'CREATED'),
                                                                          ('3', '2025-04-19 09:56', 'CONFIRMED'),
                                                                          ('3', '2025-04-19 10:00', 'IN_PROGRESS'),
                                                                          ('3', '2025-04-19 10:07', 'FINISHED'),
                                                                          ('3', '2025-04-19 10:08', 'DELIVERED');
