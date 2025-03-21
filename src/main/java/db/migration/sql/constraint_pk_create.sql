-- object: dish_pkey | type: CONSTRAINT --
-- ALTER TABLE public.dish DROP CONSTRAINT IF EXISTS dish_pkey CASCADE;
ALTER TABLE public.dish ADD CONSTRAINT dish_pkey PRIMARY KEY (id);
-- ddl-end --

-- object: ingredient_pkey | type: CONSTRAINT --
-- ALTER TABLE public.ingredient DROP CONSTRAINT IF EXISTS ingredient_pkey CASCADE;
ALTER TABLE public.ingredient ADD CONSTRAINT ingredient_pkey PRIMARY KEY (id);
-- ddl-end --

-- object: ingredient_price_pkey | type: CONSTRAINT --
-- ALTER TABLE public.ingredient_price DROP CONSTRAINT IF EXISTS ingredient_price_pkey CASCADE;
ALTER TABLE public.ingredient_price ADD CONSTRAINT ingredient_price_pkey PRIMARY KEY (ingredient_id,date);
-- ddl-end --

-- object: stock_movement_pkey | type: CONSTRAINT --
-- ALTER TABLE public.stock_movement DROP CONSTRAINT IF EXISTS stock_movement_pkey CASCADE;
ALTER TABLE public.stock_movement ADD CONSTRAINT stock_movement_pkey PRIMARY KEY (ingredient_id,date);
-- ddl-end --

-- object: make_up_pk | type: CONSTRAINT --
-- ALTER TABLE public.make_up DROP CONSTRAINT IF EXISTS make_up_pk CASCADE;
ALTER TABLE public.make_up ADD CONSTRAINT make_up_pk PRIMARY KEY (dish_id,ingredient_id);
-- ddl-end --

-- object: dish_command_pk | type: CONSTRAINT --
-- ALTER TABLE public.dish_command DROP CONSTRAINT IF EXISTS dish_command_pk CASCADE;
ALTER TABLE public.dish_command ADD CONSTRAINT dish_command_pk PRIMARY KEY (id);
-- ddl-end --

-- object: command_pk | type: CONSTRAINT --
-- ALTER TABLE public.command DROP CONSTRAINT IF EXISTS command_pk CASCADE;
ALTER TABLE public.command ADD CONSTRAINT command_pk PRIMARY KEY (id);
-- ddl-end --

-- object: command_status_pk | type: CONSTRAINT --
-- ALTER TABLE public.command_status DROP CONSTRAINT IF EXISTS command_status_pk CASCADE;
ALTER TABLE public.command_status ADD CONSTRAINT command_status_pk PRIMARY KEY (id);
-- ddl-end --

-- object: dish_command_status_history_pk | type: CONSTRAINT --
-- ALTER TABLE public.dish_command_status_history DROP CONSTRAINT IF EXISTS dish_command_status_history_pk CASCADE;
ALTER TABLE public.dish_command_status_history ADD CONSTRAINT dish_command_status_history_pk PRIMARY KEY (date,dish_command_id,status_id);
-- ddl-end --

-- object: command_status_history_pk | type: CONSTRAINT --
-- ALTER TABLE public.command_status_history DROP CONSTRAINT IF EXISTS command_status_history_pk CASCADE;
ALTER TABLE public.command_status_history ADD CONSTRAINT command_status_history_pk PRIMARY KEY (date,command_id,status_id);
-- ddl-end --

