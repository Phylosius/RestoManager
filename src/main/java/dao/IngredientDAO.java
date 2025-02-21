package dao;

import model.Ingredient;
import model.Unit;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
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
    public void add(Ingredient entity) {
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

        String sql = "SELECT * FROM ingredient ORDER BY id ASC LIMIT ? OFFSET ?";
        List<Object> params = List.of(pageSize, pageSize * (page - 1));

        BaseDAO.executeQuery(conn, sql, params, (r) -> {
            while (r.next()) {
                Ingredient ingredient = new Ingredient();

                ingredient.setId(r.getString("id"));
                ingredient.setName(r.getString("name"));
                ingredient.setModificationDate(r.getDate("modification_date").toLocalDate());
                ingredient.setUnitPrice(r.getDouble("unit_price"));
                ingredient.setUnit(Unit.valueOf(r.getString("unit")));

                ingredients.add(ingredient);
            }

        });

        return ingredients;
    }

    public static Ingredient getById(Connection conn, String id) {
        Ingredient ingredient = new Ingredient();

        String sql = "SELECT * FROM ingredient WHERE id = ?";
        List<Object> params = List.of(id);

        BaseDAO.executeQuery(conn, sql, params, (r) -> {
            if (r.next()) {
                ingredient.setId(r.getString("id"));
                ingredient.setName(r.getString("name"));
                ingredient.setModificationDate(r.getDate("modification_date").toLocalDate());
                ingredient.setUnitPrice(r.getDouble("unit_price"));
                ingredient.setUnit(Unit.valueOf(r.getString("unit")));
            }
        });

        return ingredient;
    }

    public static void add(Connection conn, Ingredient entity) {

        String sql = "INSERT INTO ingredient(id, name, modification_date, unit_price, unit) VALUES (?, ?, ?, ?, ?::ingredient_unit)";
        List<Object> params = List.of(
                entity.getId(),
                entity.getName(),
                entity.getModificationDate(),
                entity.getUnitPrice(),
                entity.getUnit().toString()
        );

        BaseDAO.executeUpdate(conn, sql, params);

    }

    public static void update(Connection conn, String id,  Ingredient entity) {

        String sql = "UPDATE ingredient SET name = ?, unit_price = ?, unit = ?::ingredient_unit, modification_date = ? WHERE id = ?";
        List<Object> params = List.of(
                entity.getName(),
                entity.getUnitPrice(),
                entity.getUnit().toString(),
                Date.valueOf(LocalDate.now()),
                id
        );

        BaseDAO.executeUpdate(conn, sql, params);

    }

    public static void delete(Connection conn, String id) {

        String sql = "DELETE FROM ingredient WHERE id = ?";
        List<Object> params = List.of(id);

        BaseDAO.executeUpdate(conn, sql, params);

    }

}
