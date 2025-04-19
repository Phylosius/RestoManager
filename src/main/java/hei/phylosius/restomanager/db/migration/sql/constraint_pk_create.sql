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
-- ALTER TABLE public.dish_order DROP CONSTRAINT IF EXISTS dish_command_pk CASCADE;
ALTER TABLE public.dish_order ADD CONSTRAINT dish_command_pk PRIMARY KEY (id);
-- ddl-end --

-- object: order_pk | type: CONSTRAINT --
-- ALTER TABLE public."order" DROP CONSTRAINT IF EXISTS order_pk CASCADE;
ALTER TABLE public."order" ADD CONSTRAINT order_pk PRIMARY KEY (id,reference);
-- ddl-end --

-- object: order_status_pk | type: CONSTRAINT --
-- ALTER TABLE public.order_status DROP CONSTRAINT IF EXISTS order_status_pk CASCADE;
ALTER TABLE public.order_status ADD CONSTRAINT order_status_pk PRIMARY KEY (id);
-- ddl-end --

-- object: dish_order_status_history_pk | type: CONSTRAINT --
-- ALTER TABLE public.dish_order_status_history DROP CONSTRAINT IF EXISTS dish_order_status_history_pk CASCADE;
ALTER TABLE public.dish_order_status_history ADD CONSTRAINT dish_order_status_history_pk PRIMARY KEY (dish_order_id,status_id);
-- ddl-end --

