\c resto_db

CREATE SEQUENCE simple_id_sequence START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE FUNCTION generate_simple_varchar_id()
RETURNS TEXT AS $$
DECLARE
    new_id INT;
BEGIN
    -- Get the next number from the sequence
    new_id := NEXTVAL('simple_id_sequence');

    -- Return the ID as a string format "n"
    RETURN new_id::TEXT;
END;
$$ LANGUAGE plpgsql;
