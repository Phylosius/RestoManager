package hei.phylosius.restomanager.Repository;

import io.github.cdimascio.dotenv.Dotenv;
import hei.phylosius.restomanager.model.Criteria;
import hei.phylosius.restomanager.model.Price;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class PriceDAO {

    private final DataSource dataSource;

    public PriceDAO() {
        Dotenv dotenv = Dotenv.load();
        dataSource = new DataSource(
                dotenv.get("DB_USERNAME"),
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_URL"));
    }

    public PriceDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addAllByIngredientId(String ingredientId, List<Price> prices) {
        prices.forEach(price -> saveByIngredientID(dataSource.getConnection(), ingredientId, price));
    }

    public void saveAllByIngredientId(String ingredientId, List<Price> prices) {
        prices.forEach(price -> saveByIngredientID(dataSource.getConnection(), ingredientId, price));
    }

    public Price getNearbyByDateAndIngredientID(LocalDateTime date, String ingredientID) {
        return getNearbyByDateAndIngredientID(dataSource.getConnection(), date, ingredientID);
    }

    public Price getLatestByIngredientID(String ingredientID) {
        return getLatestByIngredientID(dataSource.getConnection(), ingredientID);
    }

    public List<Price> getAllByIngredientID(String ingredientID) {
        if (!IngredientDAO.isExist(dataSource.getConnection(), ingredientID)) {
            throw new IngredientNotFoundException("Ingredient with id " + ingredientID + " not found.");
        }

        String sql = "select unit_price, date from ingredient_price where ingredient_id = ?";
        List<Object> params = List.of(ingredientID);

        return getAll(dataSource.getConnection(), sql, params);
    }

    public List<Price> getAllByCriteria(List<Criteria> criteria, int page, int pageSize) {
        return getAllByCriteria(dataSource.getConnection(), criteria, page, pageSize);
    }

    public static Price getNearbyByDateAndIngredientID(Connection conn, LocalDateTime date, String ingredientID) {
        Price price = new Price();

        String sql = """
                SELECT ingredient_id, unit_price, date
                FROM ingredient_price
                WHERE ingredient_id = ?
                ORDER BY ABS(EXTRACT(EPOCH FROM (date - ?::TIMESTAMP)))
                ASC
                LIMIT 1;
                """;
        List<Object> params = List.of(ingredientID, date);

        BaseDAO.executeQuery(conn, sql, params, resultSet -> {
            if (resultSet.next()) {
                price.setValue(resultSet.getDouble("unit_price"));
                price.setDate((resultSet.getTimestamp("date").toLocalDateTime()));
            }
        });

        return price;
    }

    public static List<Price> getAllByCriteria(Connection conn, List<Criteria> criteria, int page, int pageSize) {
        List<Price> result = new ArrayList<>();

        String sql = "SELECT unit_price, date from ingredient_price WHERE 1=1 ";

        BaseDAO.getAllByCriteria(conn, criteria, page, pageSize, sql, resultSet -> {
            while (resultSet.next()) {
                Price price = new Price();

                price.setValue(resultSet.getDouble("unit_price"));
                price.setDate(resultSet.getTimestamp("date").toLocalDateTime());

                result.add(price);
            }
        });

        return result;
    }

    public static List<Price> getAll(Connection conn, String sql, List<Object> params) {
        List<Price> prices = new ArrayList<>();

        BaseDAO.executeQuery(conn, sql, params, (resultSet -> {
            while (resultSet.next()) {
                Price price = new Price();

                price.setDate(resultSet.getTimestamp("date").toLocalDateTime());
                price.setValue(resultSet.getDouble("unit_price"));

                prices.add(price);
            }
        }));

        return prices;
    }

    public static Price getLatestByIngredientID(Connection conn, String ingredientID) {
        Price price = new Price();

        String sql = """
                SELECT unit_price, date
                FROM ingredient_price p
                WHERE ingredient_id = ?
                AND date = (
                    SELECT MAX(date)
                    FROM ingredient_price
                    WHERE ingredient_id = ?
                );
                
                """;
        List<Object> params = List.of(ingredientID, ingredientID);

        BaseDAO.executeQuery(conn, sql, params, (resultSet -> {
            if (resultSet.next()) {
                price.setValue(resultSet.getDouble("unit_price"));
                price.setDate(resultSet.getTimestamp("date").toLocalDateTime());

            }
        }));

        return price;
    }

    public static Price getByDateAndIngredientID(Connection conn, String ingredientID, LocalDateTime date) {
        Price price = new Price();

        String sql = "SELECT unit_price, date FROM ingredient_price WHERE date = ? AND ingredient_id = ?";
        List<Object> params = List.of( date.toString(), ingredientID);

        BaseDAO.executeQuery(conn, sql, params, (resultSet -> {
            if (resultSet.next()) {
                price.setValue(resultSet.getDouble("unit_price"));
                price.setDate(resultSet.getTimestamp("date").toLocalDateTime());
            }
        }));

        return price;
    }

    public static boolean isExist(Connection conn, String ingredientID, Price price) {
        AtomicReference<Boolean> result = new AtomicReference<>(false);

        String sql = """
                SELECT ingredient_id FROM ingredient_price WHERE ingredient_id = ? AND date = ?::TIMESTAMP
                """;
        List<Object> params = List.of(ingredientID, price.getDate().toString());

        BaseDAO.executeQuery(conn, sql, params, (resultSet -> {
            if (resultSet.next()) {
                result.set(true);
            }
        }));

        return result.get();

    }

    public static void saveByIngredientID(Connection conn, String ingredientD, Price price) {
        if (!isExist(conn, ingredientD, price)) {
            createByIngredientID(conn, ingredientD, price);
        } else {
            updateByIngredientID(conn, ingredientD, price);
        }
    }

    public static void updateByIngredientID(Connection conn, String ingredientID, Price price) {

        if (!isExist(conn, ingredientID, price)) {
            throw new PriceNotFoundException(
                    String.format("Not record for the Price of the Ingredient of id = %s at %s",
                                    ingredientID, price.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    )
            );
        }

        String sql = """
                UPDATE ingredient_price SET unit_price = ?
                WHERE ingredient_id = ? AND date = ?::TIMESTAMP
                """;
        List<Object> params = List.of(price.getValue(), ingredientID, price.getDate());

        BaseDAO.executeUpdate(conn, sql, params);
    }

    public static void createByIngredientID(Connection conn, String ingredientID, Price price) {

        if (!IngredientDAO.isExist(conn, ingredientID)){
            throw new IngredientNotFoundException(String.format("Ingredient of id = %s does not exist", ingredientID));
        }

        String sql = """
                INSERT INTO ingredient_price (ingredient_id, unit_price, date) VALUES (?, ?, ?::TIMESTAMP)
                """;
        List<Object> params = List.of(ingredientID, price.getValue(), price.getDate());

        BaseDAO.executeUpdate(conn, sql, params);
    }
}
