package hei.phylosius.restomanager.Repository;

import hei.phylosius.restomanager.model.OrderStatus;

import java.sql.Connection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class OrderStatusDAO {
    public static OrderStatus getOrderStatus(Connection conn, String statusId){
        String sql = "SELECT name FROM order_status WHERE id = ?";
        List<Object> params = List.of(statusId);
        AtomicReference<OrderStatus> orderStatus = new AtomicReference<>();

        BaseDAO.executeQuery(conn, sql, params, resultSet -> {
            if(resultSet.next()){
                orderStatus.set(OrderStatus.valueOf(resultSet.getString("name")));
            }
        });

        return orderStatus.get();

    }

    public static String getIdByOrderStatus(Connection conn, OrderStatus orderStatus){
        String sql = "SELECT id FROM order_status WHERE name = ?";
        List<Object> params = List.of(orderStatus.toString());
        AtomicReference<String> orderId = new AtomicReference<>();

        BaseDAO.executeQuery(conn, sql, params, resultSet -> {
            if(resultSet.next()){
                orderId.set(resultSet.getString("id"));
            }
        });

        return orderId.get();
    }
}
