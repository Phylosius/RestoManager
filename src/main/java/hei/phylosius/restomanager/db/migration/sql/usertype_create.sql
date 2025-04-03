-- object: public.ingredient_unit | type: TYPE --
-- DROP TYPE IF EXISTS public.ingredient_unit CASCADE;
CREATE TYPE public.ingredient_unit AS
ENUM ('L','G','U');
-- ddl-end --
ALTER TYPE public.ingredient_unit OWNER TO resto_db_user;
-- ddl-end --

-- object: public.stock_movement_type | type: TYPE --
-- DROP TYPE IF EXISTS public.stock_movement_type CASCADE;
CREATE TYPE public.stock_movement_type AS
ENUM ('OUT','IN');
-- ddl-end --
ALTER TYPE public.stock_movement_type OWNER TO resto_db_user;
-- ddl-end --

