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
public class OrderDAO {
    private DataSource dataSource;

    public List<Order> getAllByCriteria(List<Criteria> criteria, int page, int pageSize){
        return getAllByCriteria(dataSource.getConnection(), criteria, page, pageSize);
    }

    public Order getById(String id){
        return getById(dataSource.getConnection(), id);
    }

    public void save(Order order){
        save(dataSource.getConnection(), order);
    }

    public void saveAll(List<Order> orders){
        saveAll(dataSource.getConnection(), orders);
    }

    public static Order getById(Connection conn, String id){
        Criteria criteria = new Criteria(LogicalOperator.AND, "id", CriteriaOperator.EQUAL, id);
        return getAllByCriteria(conn, List.of(criteria), 1, 1).getFirst();
    }

    public static List<Order> getAllByCriteria(Connection conn, List<Criteria> criteria, int page, int pageSize){
        List <Order> dishOrders = new ArrayList<>();

        String sql = "SELECT id, created_at FROM order WHERE 1=1";
        BaseDAO.getAllByCriteria(conn, criteria, page, pageSize, sql, resultSet -> {
            while(resultSet.next()){
                Order order = new Order();
                String id =  resultSet.getString("id");

                order.setId(id);
                order.setCreationDate(resultSet.getTimestamp("created_at").toLocalDateTime());
                order.setDishOrders(
                        DishOrderDAO.getAllByOrderId(conn, id)
                );

                dishOrders.add(order);
            }
        });

        return dishOrders;
    }

    public static void saveAll(Connection conn, List<Order> orders){
        orders.forEach(order -> save(conn, order));
    }

    public static void save(Connection conn, Order order){
        if (!isExist(conn, order)) {
            add(conn, order);
        } else {
            update(conn, order);
        }
    }

    public static void add(Connection conn, Order order){
        String sql = "INSERT INTO order(id,  created_at) VALUES (?, ?)";

        List<Object> params = List.of(order.getId(), order.getCreationDate());
        BaseDAO.executeUpdate(conn, sql, params);

        DishOrderDAO.deleteByOrderId(conn, order.getId());
        DishOrderDAO.saveAll(conn, order.getDishOrders());
    }

    public static void update(Connection conn,  Order order){
        String sql = "UPDATE order SET created_at=? WHERE id=?";

        List<Object> params = List.of(order.getCreationDate(), order.getId());
        BaseDAO.executeUpdate(conn,  sql, params);

        DishOrderDAO.deleteByOrderId(conn, order.getId());
        DishOrderDAO.saveAll(conn, order.getDishOrders());
    }

    public static Boolean isExist(Connection conn, Order order){
        AtomicReference<Boolean> exist = new AtomicReference<>(false);
        String sql = "SELECT id FROM order WHERE id = ?";

        List<Object> params = List.of(order.getId());
        BaseDAO.executeQuery(conn, sql, params, resultSet -> {
            if (resultSet.next()) {
                exist.set(true);
            }
        });

        return exist.get();
    }
}
