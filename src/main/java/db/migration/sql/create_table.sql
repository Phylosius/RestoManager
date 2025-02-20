-- DB CONNECTION
\c resto_db;

-- TYPE CREATION
CREATE TYPE ingredient_unit AS ENUM ('L', 'G', 'U');

-- TABLE CREATION
-- dish
CREATE TABLE dish(
    id VARCHAR(100) PRIMARY KEY,
    name VARCHAR(100),
    unit_price FLOAT
);

-- ingredient
CREATE TABLE ingredient(
    id VARCHAR(100) PRIMARY KEY,
    name VARCHAR(100),
    modification_date TIMESTAMP,
    unit_price FLOAT,
    unit ingredient_unit
);