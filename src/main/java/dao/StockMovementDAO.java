package dao;

import model.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class StockMovementDAO{
    private final DataSource dataSource;

    public StockMovementDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public List<StockMovement> getAll(int page, int pageSize){
        return getAll(dataSource.getConnection(), page, pageSize);
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
}
