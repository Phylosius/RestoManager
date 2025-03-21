ALTER TABLE public.command_status DROP CONSTRAINT IF EXISTS command_status_pk CASCADE;
-- ddl-end --
ALTER TABLE public.command DROP CONSTRAINT IF EXISTS command_pk CASCADE;
-- ddl-end --
ALTER TABLE public.dish_command DROP CONSTRAINT IF EXISTS dish_command_pk CASCADE;
-- ddl-end --
ALTER TABLE public.make_up DROP CONSTRAINT IF EXISTS make_up_pk CASCADE;
-- ddl-end --
ALTER TABLE public.stock_movement DROP CONSTRAINT IF EXISTS stock_movement_pkey CASCADE;
-- ddl-end --
ALTER TABLE public.ingredient_price DROP CONSTRAINT IF EXISTS ingredient_price_pkey CASCADE;
-- ddl-end --
ALTER TABLE public.ingredient DROP CONSTRAINT IF EXISTS ingredient_pkey CASCADE;
-- ddl-end --
ALTER TABLE public.dish DROP CONSTRAINT IF EXISTS dish_pkey CASCADE;
-- ddl-end --
