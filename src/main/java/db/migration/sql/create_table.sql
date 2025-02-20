\c resto_db

CREATE OR REPLACE FUNCTION generate_simple_varchar_id(table_name TEXT)
RETURNS TEXT AS $$
DECLARE
    sequence_name TEXT;
    new_id INT;
    sequence_exists BOOLEAN;
BEGIN
    -- Build the sequence name dynamically
    sequence_name := table_name || '_simple_id_sequence';

    -- Check if the sequence exists
    SELECT EXISTS (
        SELECT 1 FROM pg_class WHERE relname = sequence_name
    ) INTO sequence_exists;

    -- If the sequence does not exist, create it
    IF NOT sequence_exists THEN
        EXECUTE format('CREATE SEQUENCE %I START WITH 1 INCREMENT BY 1', sequence_name);
    END IF;

    -- Get the next number from the sequence
    EXECUTE format('SELECT NEXTVAL(%I)', sequence_name) INTO new_id;

    -- Return the ID as a string format "n"
    RETURN new_id::TEXT;
END;
$$ LANGUAGE plpgsql;
