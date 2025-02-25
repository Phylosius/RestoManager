package dao;

import model.Ingredient;
import model.Unit;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO implements DataProvider<Ingredient, String> {

    private final DataSource dataSource;

    public IngredientDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Ingredient> getAll(int page, int pageSize) {
        return getAll(dataSource.getConnection(), page, pageSize);
    }

    @Override
    public Ingredient getById(String id) {
        return getById(dataSource.getConnection(), id);
    }

    @Override
    public void save(Ingredient entity) {
        add(dataSource.getConnection(), entity);
    }

    @Override
    public void update(String id, Ingredient entity) {
        update(dataSource.getConnection(), id, entity);
    }

    @Override
    public void delete(String id) {
        delete(dataSource.getConnection(), id);
    }

    public static List<Ingredient> getAll(Connection conn, int page, int pageSize) {
        List<Ingredient> ingredients = new ArrayList<>();

        String sql = """
                SELECT i.id, i.name, i.unit, p.unit_price, i.modification_date
                FROM ingredient i
                JOIN (
                    SELECT ingredient_id, unit_price, date
                    FROM ingredient_price p1
                    WHERE date = (
                        SELECT MAX(date)
                        FROM ingredient_price p2
                        WHERE p1.ingredient_id = p2.ingredient_id
                    )
                ) p ON i.id = p.ingredient_id
                ORDER BY id ASC LIMIT ? OFFSET ?""";

        List<Object> params = List.of(pageSize, pageSize * (page - 1));

        BaseDAO.executeQuery(conn, sql, params, (r) -> {
            while (r.next()) {
                Ingredient ingredient = new Ingredient();

                ingredient.setId(r.getString("id"));
                ingredient.setName(r.getString("name"));
                ingredient.setModificationDate(r.getTimestamp("modification_date").toLocalDateTime());
                ingredient.setPrice(IngredientPriceDAO.getLatestByIngredientID(conn, ingredient.getId()));
                ingredient.setUnit(Unit.valueOf(r.getString("unit")));

                ingredients.add(ingredient);
            }

        });

        return ingredients;
    }

    public static Ingredient getById(Connection conn, String id) {
        Ingredient ingredient = new Ingredient();

        String sql = """
                SELECT i.id, i.name, i.unit, p.unit_price, i.modification_date
                FROM ingredient i
                JOIN (
                    SELECT ingredient_id, unit_price, date
                    FROM ingredient_price p1
                    WHERE date = (
                        SELECT MAX(date)
                        FROM ingredient_price p2
                        WHERE p1.ingredient_id = p2.ingredient_id
                    )
                ) p ON i.id = p.ingredient_id
                WHERE i.id = ?
                """;
        List<Object> params = List.of(id);

        BaseDAO.executeQuery(conn, sql, params, (r) -> {
            if (r.next()) {
                ingredient.setId(r.getString("id"));
                ingredient.setName(r.getString("name"));
                ingredient.setModificationDate(r.getTimestamp("modification_date").toLocalDateTime());
                ingredient.setPrice(IngredientPriceDAO.getLatestByIngredientID(conn, ingredient.getId()));
                ingredient.setUnit(Unit.valueOf(r.getString("unit")));
            }
        });

        return ingredient;
    }

    public static void add(Connection conn, Ingredient entity) {

        String sql = "INSERT INTO ingredient(id, name, modification_date, unit) VALUES (?, ?, ?, ?::ingredient_unit)";
        List<Object> params = List.of(
                entity.getId(),
                entity.getName(),
                entity.getModificationDate(),
                entity.getUnit().toString()
        );

        BaseDAO.executeUpdate(conn, sql, params);
        IngredientPriceDAO.saveByIngredientID(conn, entity.getId(), entity.getPrice());
    }

    public static void update(Connection conn, String id,  Ingredient entity) {

        String sql = "UPDATE ingredient SET name = ?, unit = ?::ingredient_unit, modification_date = ? WHERE id = ?";
        List<Object> params = List.of(
                entity.getName(),
                entity.getUnit().toString(),
                LocalDateTime.now(),
                id
        );

        BaseDAO.executeUpdate(conn, sql, params);
        IngredientPriceDAO.saveByIngredientID(conn, entity.getId(), entity.getPrice());

    }

    public static void delete(Connection conn, String id) {

        String sql = "DELETE FROM ingredient WHERE id = ?";
        List<Object> params = List.of(id);

        BaseDAO.executeUpdate(conn, sql, params);

    }

}
