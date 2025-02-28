package dao;

import io.github.cdimascio.dotenv.Dotenv;
import model.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class StockMovementDAO{
    private final DataSource dataSource;

    public StockMovementDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public StockMovementDAO() {
        Dotenv dotenv = Dotenv.load();
        dataSource = new DataSource(
                dotenv.get("DB_USERNAME"),
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_URL")
        );
    }

    public List<StockMovement> getAll(int page, int pageSize){
        return getAll(dataSource.getConnection(), page, pageSize);
    }

    public List<StockMovement> getByIngredientID(String ingredientID, int page, int pageSize){
        return getByIngredientID(dataSource.getConnection(), ingredientID, page, pageSize);
    }

    public List<StockMovement> getAllByCriteria(List<Criteria> criteria, int page, int pageSize){
        return getAllByCriteria(dataSource.getConnection(), criteria, page, pageSize);
    }

    public int save(StockMovement stockMovement){
        return save(dataSource.getConnection(), stockMovement);
    }

    public int saveAll(List<StockMovement> stockMovements){
        return saveAll(dataSource.getConnection(), stockMovements);
    }

    public static List<StockMovement> getAll(Connection conn, int page, int pageSize){
        return getAllByCriteria(conn, List.of(), page, pageSize);
    }

    public static List<StockMovement> getByIngredientID(Connection conn, String ingredientID, int page, int pageSize){
        Criteria criteria = new Criteria(LogicalOperator.AND, "ingredient_id", CriteriaOperator.EQUAL, ingredientID);

        return getAllByCriteria(conn, List.of(criteria), page, pageSize);
    }

    public static List<StockMovement> getAllByCriteria(Connection conn, List<Criteria> criteria, int page, int pageSize){
        List<StockMovement> stockMovements = new ArrayList<>();

        String sql = "SELECT ingredient_id, type, quantity, date FROM stock_movement WHERE 1=1";

        BaseDAO.getAllByCriteria(conn, criteria, page, pageSize, sql, result -> {
            while(result.next()){
                StockMovement movement = new StockMovement();

                movement.setAffectedIngredient(
                        IngredientDAO.getById(conn,
                                result.getString("ingredient_id")));
                movement.setType(
                        MovementType.valueOf(
                                result.getString("type")));
                movement.setQuantity(
                        result.getDouble("quantity"));
                movement.setDate(
                        result.getTimestamp("date").toLocalDateTime());

                stockMovements.add(movement);
            }
        });

        return stockMovements;
    }

    public static int save(Connection conn, StockMovement stockMovement){
        return saveAll(conn, List.of(stockMovement));
    }

    public static int saveAll(Connection conn, List<StockMovement> stockMovements){
        int saved;

        String sql = "INSERT INTO stock_movement (ingredient_id, type, quantity, date) VALUES ";
        StringBuilder sqlBuilder = new StringBuilder(sql);
        List<Object> params = new ArrayList<>();

        for (int i = 0; i < stockMovements.size(); i++) {
            StockMovement movement = stockMovements.get(i);

            sqlBuilder.append(stockMovements.size() - i > 1 ? "(?, ?, ?, ?)," : "(?, ?, ?, ?)");
            params.addAll(List.of(
                    movement.getAffectedIngredient().getId(),
                    movement.getType().toString(),
                    movement.getQuantity(),
                    movement.getDate()
            ));
        }

        sql = sqlBuilder.toString();

        saved = BaseDAO.executeUpdate(conn, sql, params);

        return saved;
    }
}
