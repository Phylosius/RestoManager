-- object: public.dish | type: TABLE --
-- DROP TABLE IF EXISTS public.dish CASCADE;
CREATE TABLE public.dish (
	id character varying(100) NOT NULL DEFAULT generate_simple_varchar_id('dish'::text),
	name character varying(100),
	unit_price double precision

);
-- ddl-end --
ALTER TABLE public.dish OWNER TO resto_db_user;
-- ddl-end --

-- object: public.ingredient | type: TABLE --
-- DROP TABLE IF EXISTS public.ingredient CASCADE;
CREATE TABLE public.ingredient (
	id character varying(100) NOT NULL DEFAULT generate_simple_varchar_id('ingredient'::text),
	name character varying(100),
	modification_date timestamp,
	unit public.ingredient_unit

);
-- ddl-end --
ALTER TABLE public.ingredient OWNER TO resto_db_user;
-- ddl-end --

-- object: public.ingredient_price | type: TABLE --
-- DROP TABLE IF EXISTS public.ingredient_price CASCADE;
CREATE TABLE public.ingredient_price (
	ingredient_id character varying(100) NOT NULL,
	unit_price double precision,
	date timestamp NOT NULL

);
-- ddl-end --
ALTER TABLE public.ingredient_price OWNER TO resto_db_user;
-- ddl-end --

-- object: public.stock_movement | type: TABLE --
-- DROP TABLE IF EXISTS public.stock_movement CASCADE;
CREATE TABLE public.stock_movement (
	ingredient_id character varying(100) NOT NULL,
	type public.stock_movement_type,
	quantity double precision,
	date timestamp NOT NULL

);
-- ddl-end --
ALTER TABLE public.stock_movement OWNER TO resto_db_user;
-- ddl-end --

-- object: public.make_up | type: TABLE --
-- DROP TABLE IF EXISTS public.make_up CASCADE;
CREATE TABLE public.make_up (
	dish_id character varying(100) NOT NULL,
	ingredient_id character varying(100) NOT NULL,
	ingredient_quantity double precision

);
-- ddl-end --
ALTER TABLE public.make_up OWNER TO resto_db_user;
-- ddl-end --

-- object: public.dish_command | type: TABLE --
-- DROP TABLE IF EXISTS public.dish_command CASCADE;
CREATE TABLE public.dish_command (
	id varchar(100) NOT NULL,
	status_id varchar(100),
	dish_id varchar(100),
	command_id varchar(100)

);
-- ddl-end --
ALTER TABLE public.dish_command OWNER TO resto_db_user;
-- ddl-end --

-- object: public.command | type: TABLE --
-- DROP TABLE IF EXISTS public.command CASCADE;
CREATE TABLE public.command (
	id varchar(100) NOT NULL,
	created_at timestamp DEFAULT CURRENT_DATE::TIMESTAMP,
	status_id varchar(100)

);
-- ddl-end --
ALTER TABLE public.command OWNER TO resto_db_user;
-- ddl-end --

-- object: public.command_status | type: TABLE --
-- DROP TABLE IF EXISTS public.command_status CASCADE;
CREATE TABLE public.command_status (
	id varchar(100) NOT NULL,
	"order" smallint,
	name varchar(100)

);
-- ddl-end --
ALTER TABLE public.command_status OWNER TO resto_db_user;
-- ddl-end --

