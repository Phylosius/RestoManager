package dao;

import model.Criteria;
import model.MovementType;
import model.StockMovement;

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
        List<StockMovement> stockMovements = new ArrayList<>();

        String sql = """
                SELECT ingredient_id, type, quantity, date
                FROM stock_movement
                LIMIT ? OFFSET ?
                """;
        List<Object> params = List.of(pageSize, pageSize * (page - 1));

        BaseDAO.executeQuery(conn, sql, params, resultSet -> {
            while(resultSet.next()){
                StockMovement stockMovement = new StockMovement();

                stockMovement.setDate(resultSet.getTimestamp("date").toLocalDateTime());
                stockMovement.setType(MovementType.valueOf(resultSet.getString("type")));
                stockMovement.setQuantity(resultSet.getDouble("quantity"));
                stockMovement.setAffectedIngredient(
                        IngredientDAO.getById(conn, resultSet.getString("ingredient_id"))
                );

                stockMovements.add(stockMovement);
            }
        });

        return stockMovements;
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

    public static List<StockMovement> getByIngredientID(Connection conn, String ingredientID, int page, int pageSize){
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
