ALTER TABLE public.command DROP CONSTRAINT IF EXISTS fk_command_command_status_id CASCADE;
-- ddl-end --
ALTER TABLE public.dish_command DROP CONSTRAINT IF EXISTS fk_dish_command_command_status_id CASCADE;
-- ddl-end --
ALTER TABLE public.dish_command DROP CONSTRAINT IF EXISTS fk_dish_command_command_id CASCADE;
-- ddl-end --
ALTER TABLE public.dish_command DROP CONSTRAINT IF EXISTS fk_dish_command_dish_id CASCADE;
-- ddl-end --
ALTER TABLE public.stock_movement DROP CONSTRAINT IF EXISTS fk_stock_movement_ingredient_id CASCADE;
-- ddl-end --
ALTER TABLE public.ingredient_price DROP CONSTRAINT IF EXISTS fk_ingredient_price_ingredient_id CASCADE;
-- ddl-end --
ALTER TABLE public.make_up DROP CONSTRAINT IF EXISTS fk_make_up_ingredient_id CASCADE;
-- ddl-end --
ALTER TABLE public.make_up DROP CONSTRAINT IF EXISTS fk_make_up_dish_id CASCADE;
-- ddl-end --
