package dao;

import io.github.cdimascio.dotenv.Dotenv;
import model.*;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class IngredientDAO implements DataProvider<Ingredient, String> {

    private final DataSource dataSource;

    public IngredientDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public IngredientDAO() {
        Dotenv dotenv = Dotenv.load();
        dataSource = new DataSource(
                dotenv.get("DB_USERNAME"),
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_URL")
        );
    }

    public List<Ingredient> getAllByCriteria(List<Criteria> criteria, int page, int pageSize) {
        return getAllByCriteria(dataSource.getConnection(), criteria, page, pageSize);
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

    public static void save(Connection conn, Ingredient entity) {
        if(isExist(conn, entity.getId())) {
            update(conn, entity.getId(), entity);
        } else {
            add(conn, entity);
        }
    }

    public static List<Ingredient> getAll(Connection conn, int page, int pageSize) {
        return getAllByCriteria(conn, List.of(), page, pageSize);
    }

    public static Ingredient getById(Connection conn, String id) {
        Criteria criteria = new Criteria(LogicalOperator.AND, "id", CriteriaOperator.EQUAL, id);
        return getAllByCriteria(conn, List.of(criteria), 1, 1).getFirst();
    }

    public static List<Ingredient> getAllByCriteria(Connection conn, List<Criteria> criteria, int page, int pageSize) {
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
                WHERE 1=1
                """;

        BaseDAO.getAllByCriteria(conn, criteria, page, pageSize, sql, resultSet -> {
            while (resultSet.next()) {
                Ingredient ingredient = new Ingredient();

                ingredient.setId(resultSet.getString("id"));
                ingredient.setName(resultSet.getString("name"));
                ingredient.setModificationDate(resultSet.getTimestamp("modification_date").toLocalDateTime());
                ingredient.setPrice(PriceDAO.getLatestByIngredientID(conn, ingredient.getId()));
                ingredient.setUnit(Unit.valueOf(resultSet.getString("unit")));

                ingredients.add(ingredient);
            }
        });

        return ingredients;
    }

    public static Boolean isExist(Connection conn, String id) {
        AtomicBoolean truth = new AtomicBoolean(false);

        String sql = "SELECT id FROM ingredient WHERE id = ?";

        BaseDAO.executeQuery(conn, sql, List.of(id), resultSet -> {
            if (resultSet.next()) {
                truth.set(true);
            }
        });

        return truth.get();
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
        PriceDAO.saveByIngredientID(conn, entity.getId(), entity.getPrice());
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
        PriceDAO.saveByIngredientID(conn, entity.getId(), entity.getPrice());

    }

    public static void delete(Connection conn, String id) {

        String sql = "DELETE FROM ingredient WHERE id = ?";
        List<Object> params = List.of(id);

        BaseDAO.executeUpdate(conn, sql, params);

    }

}
