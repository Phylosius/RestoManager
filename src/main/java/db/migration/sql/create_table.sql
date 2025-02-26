-- DB CONNECTION
\c resto_db;

-- TYPE CREATION
CREATE TYPE ingredient_unit AS ENUM ('L', 'G', 'U');

-- TABLE CREATION
-- dish
CREATE TABLE dish(
    id VARCHAR(100) PRIMARY KEY DEFAULT generate_simple_varchar_id('dish'),
    name VARCHAR(100),
    unit_price FLOAT
);

-- ingredient
CREATE TABLE ingredient(
    id VARCHAR(100) PRIMARY KEY DEFAULT generate_simple_varchar_id('ingredient'),
    name VARCHAR(100),
    modification_date TIMESTAMP,
    unit ingredient_unit
);

-- ingredient_price
CREATE TABLE ingredient_price(
    ingredient_id VARCHAR(100),
    unit_price FLOAT,
    date TIMESTAMP,

    PRIMARY KEY (ingredient_id, date)
);

-- stock_movement
CREATE TYPE stock_movement_type AS ENUM ('OUT', 'IN');

CREATE TABLE stock_movement(
    ingredient_id VARCHAR(100),
    type stock_movement_type,
    quantity FLOAT,
    date TIMESTAMP,

    PRIMARY KEY (ingredient_id, date)
);
