package hei.phylosius.restomanager.Repository;

import io.github.cdimascio.dotenv.Dotenv;
import hei.phylosius.restomanager.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Repository
public class IngredientDAO implements DataProvider<Ingredient, String> {

    private final DataSource dataSource;

    @Autowired
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

    public Ingredient getByName(String name) {
        Criteria nameCriteria = new Criteria(LogicalOperator.AND, "i.name", CriteriaOperator.EQUAL, name);
        return getAllByCriteria(List.of(nameCriteria)).getFirst();
    }

    public List<Ingredient> getAllByCriteria(List<Criteria> criteria, int page, int pageSize) {
        return getAllByCriteria(dataSource.getConnection(), criteria, page, pageSize);
    }

    public List<Ingredient> getAllByCriteria(List<Criteria> criteria) {
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

        BaseDAO.getAllByCriteria(dataSource.getConnection(), criteria, sql, resultSet -> {
            while (resultSet.next()) {
                Ingredient ingredient = new Ingredient();

                ingredient.setId(resultSet.getString("id"));
                ingredient.setName(resultSet.getString("name"));
                ingredient.setModificationDate(resultSet.getTimestamp("modification_date").toLocalDateTime());
                ingredient.setPrice(PriceDAO.getLatestByIngredientID(dataSource.getConnection(), ingredient.getId()));
                ingredient.setUnit(Unit.valueOf(resultSet.getString("unit")));

                ingredients.add(ingredient);
            }
        });

        return ingredients;
    }

    @Override
    public List<Ingredient> getAll(int page, int pageSize) {
        return getAll(dataSource.getConnection(), page, pageSize);
    }

    public List<Ingredient> getAll() {
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
                """;

        BaseDAO.executeQuery(dataSource.getConnection(), sql, List.of(), result -> {
            while (result.next()) {
                Ingredient ingredient = new Ingredient();

                ingredient.setId(result.getString("id"));
                ingredient.setName(result.getString("name"));
                ingredient.setModificationDate(result.getTimestamp("modification_date").toLocalDateTime());
                ingredient.setPrice(PriceDAO.getLatestByIngredientID(dataSource.getConnection(), ingredient.getId()));
                ingredient.setUnit(Unit.valueOf(result.getString("unit")));

                ingredients.add(ingredient);
            }
        });

        return ingredients;
    }

    @Override
    public Ingredient getById(String id) {
        return getById(dataSource.getConnection(), id);
    }

    public void saveAll(List<Ingredient> ingredients) {
        ingredients.forEach(this::save);
    }

    @Override
    public void save(Ingredient entity) {
        save(dataSource.getConnection(), entity);
    }

    public void addAll(List<Ingredient> ingredients) {
        ingredients.forEach(this::add);
    }

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
        List<Ingredient> ingredients = getAllByCriteria(conn, List.of(new Criteria(LogicalOperator.AND, "id", CriteriaOperator.EQUAL, id)), 1, 1);
        return ingredients.isEmpty() ? null : ingredients.getFirst();
    }

    public static List<Ingredient> getAllByCriteria(Connection conn, List<Criteria> criteria, int page, int pageSize) {
        List<Ingredient> ingredients = new ArrayList<>();
//
//        if (page < 1) {
//            throw new IllegalPageValueException("The value of the parameter page should be greater than 0.");
//        }
//
//        if (pageSize < 1) {
//            throw new IllegalPageSizeValueException("The value of the parameter pageSize should be greater than 0.");
//        }

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
        if (entity.getPrice() != null) {
            PriceDAO.saveByIngredientID(conn, entity.getId(), entity.getPrice());
        }
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
        if (entity.getPrice() != null) {
            PriceDAO.saveByIngredientID(conn, entity.getId(), entity.getPrice());
        }

    }

    public static void delete(Connection conn, String id) {

        String sql = "DELETE FROM ingredient WHERE id = ?";
        List<Object> params = List.of(id);

        BaseDAO.executeUpdate(conn, sql, params);

    }

}
