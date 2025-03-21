package dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@AllArgsConstructor
public class DishOrderDAO {
    private DataSource dataSource;

    public List<DishOrder> getAllByCriteria(List<Criteria> criteria, int page, int pageSize) {
        return getAllByCriteria(dataSource.getConnection(), criteria, page, pageSize);
    }

    public void save(DishOrder dishOrder){
        save(dataSource.getConnection(), dishOrder);
    }

    public void saveAll(List<DishOrder> dishOrders){
        saveAll(dataSource.getConnection(), dishOrders);
    }

    public static List<DishOrder> getAllByOrderId(Connection conn, String orderId) {
        List<DishOrder> dishOrders = new ArrayList<>();

        String sql = "SELECT id, dish_id, order_id, quantity FROM dish_order WHERE order_id = ?";
        List<Object> params = List.of(orderId);
        BaseDAO.executeQuery(conn, sql, params,  result -> {
            while (result.next()) {
                DishOrder dishOrder = new DishOrder();
                String id = result.getString("id");

                dishOrder.setId(id);
                dishOrder.setOrderId(result.getString("order_id"));
                dishOrder.setQuantity(result.getInt("quantity"));
                dishOrder.setStatusHistory(
                        new OrderStatusHistory(
                                OrderStatusRecordDAO.getAllByDishOrderId(conn, id)
                        )
                );

                dishOrders.add(dishOrder);
            }
        });

        return dishOrders;
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

    public static void saveAll(Connection conn,  List<DishOrder> dishOrders) {
        dishOrders.forEach(dishOrder -> save(conn, dishOrder));
    }

    public static void save(Connection conn,  DishOrder dishOrder){
        if (!isExist(conn, dishOrder)) {
            add(conn, dishOrder);
        } else {
            update(conn, dishOrder.getId(), dishOrder);
        }
    }

    public static void add(Connection conn, DishOrder dishOrder){
        String  sql = "INSERT INTO dish_order (id, quantity, order_id, dish_id) VALUES (?, ?, ?, ?)";

        List<Object> params = List.of(dishOrder.getId(), dishOrder.getQuantity(), dishOrder.getOrderId(), dishOrder.getDish().getId());
        BaseDAO.executeUpdate(conn, sql, params);
        OrderStatusRecordDAO.saveAll(conn, dishOrder.getStatusHistory().getRecords());
    }

    public static void update(Connection conn, String id, DishOrder dishOrder){
        String  sql = "UPDATE dish_order SET quantity = ?, order_id = ?, dish_id = ? WHERE id = ?";

        List<Object> params = List.of(dishOrder.getQuantity(), dishOrder.getOrderId(), dishOrder.getDish().getId(), id);
        BaseDAO.executeUpdate(conn, sql, params);
        OrderStatusRecordDAO.saveAll(conn, dishOrder.getStatusHistory().getRecords());
    }

    public static Boolean isExist(Connection conn, DishOrder dishOrder){
        AtomicReference<Boolean> exist = new AtomicReference<>(false);
        String sql = "SELECT id FROM  dish_order WHERE dish_id = ? AND order_id = ?";

        List<Object> params = List.of(dishOrder.getId(), dishOrder.getOrderId());
        BaseDAO.executeQuery(conn, sql, params, resultSet -> {
            if (resultSet.next()) {
                exist.set(true);
            }
        });

        return exist.get();
    }

    public static void deleteByOrderId(Connection conn, String orderId){
        String sql = "DELETE FROM dish_order WHERE order_id = ?";

        List<Object> params = List.of(orderId);
        BaseDAO.executeUpdate(conn, sql, params);
    }
}
