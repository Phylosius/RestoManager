\c resto_db

CREATE SEQUENCE dish_simple_id_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE ingredient_simple_id_sequence START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE FUNCTION generate_simple_varchar_id(table_name TEXT)
RETURNS TEXT AS $$
DECLARE
    new_id INT;
BEGIN
    -- Get the next number from the corresponding sequence
    EXECUTE format('SELECT NEXTVAL(%L || ''_simple_id_sequence'')', table_name) INTO new_id;

    -- Return the ID as a string format "n"
    RETURN new_id::TEXT;
END;
$$ LANGUAGE plpgsql;
