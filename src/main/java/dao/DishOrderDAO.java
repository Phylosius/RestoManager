package dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class DishOrderDAO {
    private DataSource dataSource;

    public List<DishOrder> getAllByCriteria(List<Criteria> criteria, int page, int pageSize) {
        return getAllByCriteria(dataSource.getConnection(), criteria, page, pageSize);
    }

    public static List<DishOrder> getAllByOrderId(Connection conn, String orderId, int page, int pageSize) {
        Criteria criteria = new Criteria(LogicalOperator.AND, "order_id", CriteriaOperator.EQUAL, orderId);
        return getAllByCriteria(conn, List.of(criteria), page, pageSize);
    }

    public static List<DishOrder> getAllByCriteria(Connection conn, List<Criteria> criteria, int page, int pageSize) {
        List <DishOrder> dishOrders = new ArrayList<>();

        String sql = "SELECT id, dish_id, order_id, quantity FROM dish_order WHERE 1=1";
        BaseDAO.getAllByCriteria(conn, criteria, page, pageSize, sql, resultSet -> {
            while (resultSet.next()) {
                DishOrder dishOrder = new DishOrder();
                String dishId = resultSet.getString("dish_id");
                String id = resultSet.getString("id");

                dishOrder.setId(id);
                dishOrder.setOrderId(resultSet.getString("order_id"));
                dishOrder.setDish(
                        DishDAO.getById(conn, dishId)
                );
                dishOrder.setStatusHistory(
                        new OrderStatusHistory(OrderStatusRecordDAO.getAllByDishOrderId(conn, id))
                );
                dishOrder.setQuantity(resultSet.getInt("quantity"));

                dishOrders.add(dishOrder);
            }
        });

        return dishOrders;
    }
}
