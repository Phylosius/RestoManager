package dao;

import model.Ingredient;
import model.Unit;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IngredientDAO implements DataProvider<Ingredient, String> {

    private DataSource dataSource;

    public IngredientDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Ingredient> getAll(Connection conn, int page, int pageSize) {
        List<Ingredient> ingredients = new ArrayList<>();

        String sql = "SELECT * FROM ingredient ORDER BY id DESC LIMIT ? OFFSET ?";
        List<Object> params = List.of(pageSize, page * (pageSize - 1));

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

    @Override
    public Ingredient getById(Connection conn, String id) {
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

    @Override
    public void add(Connection conn, Ingredient entity) {

        String sql = "INSERT INTO ingredient(id, name, modification_date, unit_price, unit) VALUES (?, ?, ?, ?, ?)";
        List<Object> params = List.of(
                entity.getId(),
                entity.getName(),
                entity.getModificationDate(),
                entity.getUnitPrice(),
                entity.getUnit().toString()
        );

        BaseDAO.executeUpdate(conn, sql, params);

    }

    @Override
    public void update(Connection conn, Ingredient entity) {

        String sql = "UPDATE ingredient SET name = ?, unit_price = ?, unit = ?, modification_date = ? WHERE id = ?";
        List<Object> params = List.of(
                entity.getName(),
                entity.getUnitPrice(),
                entity.getUnit().toString(),
                Date.valueOf(LocalDate.now()),
                entity.getId()
        );

        BaseDAO.executeUpdate(conn, sql, params);

    }

    @Override
    public void delete(Connection conn, String id) {

        String sql = "DELETE FROM ingredient WHERE id = ?";
        List<Object> params = List.of(id);

        BaseDAO.executeUpdate(conn, sql, params);

    }
}
