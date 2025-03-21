-- object: fk_ingredient_price_ingredient_id | type: CONSTRAINT --
-- ALTER TABLE public.ingredient_price DROP CONSTRAINT IF EXISTS fk_ingredient_price_ingredient_id CASCADE;
ALTER TABLE public.ingredient_price ADD CONSTRAINT fk_ingredient_price_ingredient_id FOREIGN KEY (ingredient_id)
REFERENCES public.ingredient (id) MATCH SIMPLE
ON DELETE CASCADE ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_stock_movement_ingredient_id | type: CONSTRAINT --
-- ALTER TABLE public.stock_movement DROP CONSTRAINT IF EXISTS fk_stock_movement_ingredient_id CASCADE;
ALTER TABLE public.stock_movement ADD CONSTRAINT fk_stock_movement_ingredient_id FOREIGN KEY (ingredient_id)
REFERENCES public.ingredient (id) MATCH SIMPLE
ON DELETE CASCADE ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_make_up_dish_id | type: CONSTRAINT --
-- ALTER TABLE public.make_up DROP CONSTRAINT IF EXISTS fk_make_up_dish_id CASCADE;
ALTER TABLE public.make_up ADD CONSTRAINT fk_make_up_dish_id FOREIGN KEY (dish_id)
REFERENCES public.dish (id) MATCH SIMPLE
ON DELETE CASCADE ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_make_up_ingredient_id | type: CONSTRAINT --
-- ALTER TABLE public.make_up DROP CONSTRAINT IF EXISTS fk_make_up_ingredient_id CASCADE;
ALTER TABLE public.make_up ADD CONSTRAINT fk_make_up_ingredient_id FOREIGN KEY (ingredient_id)
REFERENCES public.ingredient (id) MATCH SIMPLE
ON DELETE CASCADE ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_dish_command_dish_id | type: CONSTRAINT --
-- ALTER TABLE public.dish_order DROP CONSTRAINT IF EXISTS fk_dish_command_dish_id CASCADE;
ALTER TABLE public.dish_order ADD CONSTRAINT fk_dish_command_dish_id FOREIGN KEY (dish_id)
REFERENCES public."dish" (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_dish_command_command_id | type: CONSTRAINT --
-- ALTER TABLE public.dish_order DROP CONSTRAINT IF EXISTS fk_dish_command_command_id CASCADE;
ALTER TABLE public.dish_order ADD CONSTRAINT fk_dish_command_command_id FOREIGN KEY (order_id)
REFERENCES public."order" (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_dish_order_history_dish_order_id | type: CONSTRAINT --
-- ALTER TABLE public.dish_order_status_history DROP CONSTRAINT IF EXISTS fk_dish_order_history_dish_order_id CASCADE;
ALTER TABLE public.dish_order_status_history ADD CONSTRAINT fk_dish_order_history_dish_order_id FOREIGN KEY (dish_order_id)
REFERENCES public.dish_order (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_dish_order_status_history_order_status_id | type: CONSTRAINT --
-- ALTER TABLE public.dish_order_status_history DROP CONSTRAINT IF EXISTS fk_dish_order_status_history_order_status_id CASCADE;
ALTER TABLE public.dish_order_status_history ADD CONSTRAINT fk_dish_order_status_history_order_status_id FOREIGN KEY (status_id)
REFERENCES public.order_status (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

