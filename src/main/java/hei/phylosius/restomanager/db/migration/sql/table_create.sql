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

-- object: public.dish_order | type: TABLE --
-- DROP TABLE IF EXISTS public.dish_order CASCADE;
CREATE TABLE public.dish_order (
	id character varying(100) NOT NULL,
	dish_id character varying(100),
	order_id character varying(100),
	quantity integer DEFAULT 1

);
-- ddl-end --
ALTER TABLE public.dish_order OWNER TO resto_db_user;
-- ddl-end --

-- object: public."order" | type: TABLE --
-- DROP TABLE IF EXISTS public."order" CASCADE;
CREATE TABLE public."order" (
	id character varying(100) NOT NULL,
	created_at timestamp DEFAULT (CURRENT_DATE)::timestamp without time zone,
	reference varchar NOT NULL

);
-- ddl-end --
ALTER TABLE public."order" OWNER TO resto_db_user;
-- ddl-end --

-- object: public.order_status | type: TABLE --
-- DROP TABLE IF EXISTS public.order_status CASCADE;
CREATE TABLE public.order_status (
	id character varying(100) NOT NULL DEFAULT uuid_generate_v4(),
	"order" smallint,
	name character varying(100)

);
-- ddl-end --
ALTER TABLE public.order_status OWNER TO resto_db_user;
-- ddl-end --

-- object: public.dish_order_status_history | type: TABLE --
-- DROP TABLE IF EXISTS public.dish_order_status_history CASCADE;
CREATE TABLE public.dish_order_status_history (
	date timestamp DEFAULT (CURRENT_DATE)::timestamp without time zone,
	dish_order_id character varying(100) NOT NULL,
	status_id character varying(100) NOT NULL

);
-- ddl-end --
ALTER TABLE public.dish_order_status_history OWNER TO resto_db_user;
-- ddl-end --

