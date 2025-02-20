-- DB CONNECTION
\c resto_db;

-- RELATION CREATION
-- make_up
CREATE TABLE make_up(
    dish_id VARCHAR(100),
    ingredient_id VARCHAR(100),
    ingredient_quantity FLOAT
);
-- make_up constraint
-- make_up primary key
ALTER TABLE make_up
    ADD CONSTRAINT make_up_pk
        PRIMARY KEY (dish_id, ingredient_id);

-- make_up and disk fk
ALTER TABLE make_up
ADD CONSTRAINT fk_make_up_dish_id
    FOREIGN KEY (dish_id) REFERENCES dish(id)
        ON DELETE CASCADE;

-- make_up and ingredient fk
ALTER TABLE make_up
ADD CONSTRAINT fk_make_up_ingredient_id
    FOREIGN KEY (ingredient_id) REFERENCES ingredient(id)
        ON DELETE CASCADE;