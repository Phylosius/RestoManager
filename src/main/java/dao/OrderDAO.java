package dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

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
}
