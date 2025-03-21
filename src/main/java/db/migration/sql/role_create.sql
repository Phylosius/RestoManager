-- object: resto_db_user | type: ROLE --
-- DROP ROLE IF EXISTS resto_db_user;
CREATE ROLE resto_db_user WITH 
	INHERIT
	LOGIN
	 PASSWORD '********';
-- ddl-end --

