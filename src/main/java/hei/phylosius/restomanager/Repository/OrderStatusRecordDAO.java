package hei.phylosius.restomanager.Repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import hei.phylosius.restomanager.model.Criteria;
import hei.phylosius.restomanager.model.CriteriaOperator;
import hei.phylosius.restomanager.model.LogicalOperator;
import hei.phylosius.restomanager.model.OrderStatusRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Repository
public class OrderStatusRecordDAO{

    @Autowired
    private DataSource dataSource;

    public List<OrderStatusRecord> getAllByCriteria(List<Criteria> criteria, int page, int pageSize) {
        return getAllByCriteria(dataSource.getConnection(), criteria, page, pageSize);
    }

    public void save(OrderStatusRecord statusRecord){
        save(dataSource.getConnection(), statusRecord);
    }

    public List<OrderStatusRecord> getAllByDishOrderId(String dishOrderId) {
        return getAllByDishOrderId(dataSource.getConnection(), dishOrderId);
    }

    public static List<OrderStatusRecord> getAllByDishOrderId(Connection conn, String dishOrderId) {
        Criteria criteria = new Criteria(LogicalOperator.AND, "dish_order_id", CriteriaOperator.EQUAL, dishOrderId);
        return getAllByCriteria(conn, List.of(criteria), 1, 5);
    }

    public static List<OrderStatusRecord> getAllByCriteria(Connection conn, List<Criteria> criteria, int page, int pageSize) {
        List<OrderStatusRecord> statusRecords = new ArrayList<>();

        String sql =  "SELECT dish_order_id, date, status_id FROM dish_order_status_history WHERE 1=1";

        BaseDAO.getAllByCriteria(conn, criteria, page, pageSize, sql, resultSet -> {
            while (resultSet.next()) {
                OrderStatusRecord orderStatusRecord = new OrderStatusRecord();

                orderStatusRecord.setDishOrderId(resultSet.getString("dish_order_id"));
                orderStatusRecord.setStatus(
                        OrderStatusDAO.getOrderStatus(conn, resultSet.getString("status_id"))
                );
                orderStatusRecord.setDate(resultSet.getTimestamp("date").toLocalDateTime());

                statusRecords.add(orderStatusRecord);
            }
        });

        return statusRecords;
    }

    public static void saveAll(Connection conn, List<OrderStatusRecord> records) {
        records.forEach(r -> save(conn, r));
    }

    public static void save(Connection conn, OrderStatusRecord statusRecord){
        if (!isExist(conn, statusRecord)) {
            add(conn, statusRecord);
        } else {
            throw new RuntimeException("Status record already exists");
        }
    }

    public static void add(Connection conn, OrderStatusRecord statusRecord) {
        String sql = "INSERT INTO dish_order_status_history(date, dish_order_id, status_id) VALUES (?, ?, ?)";

        List<Object> params = List.of(
                statusRecord.getDate(),
                statusRecord.getDishOrderId(),
                OrderStatusDAO.getIdByOrderStatus(conn, statusRecord.getStatus())
        );
        BaseDAO.executeUpdate(conn, sql, params);
    }

    public static Boolean isExist(Connection conn, OrderStatusRecord statusRecord) {
        String sql = "SELECT date FROM  dish_order_status_history WHERE status_id = ? AND dish_order_id = ?";
        List<Object> params = List.of(
                OrderStatusDAO.getIdByOrderStatus(conn, statusRecord.getStatus()),
                statusRecord.getDishOrderId()
        );

        AtomicReference<Boolean> result = new AtomicReference<>(false);

        BaseDAO.executeQuery(conn, sql, params, resultSet -> {
            if (resultSet.next()) {
                result.set(true);
            }
        });

        return result.get();
    }

    public static int deleteAllByDishOrderId(Connection conn, String dishOrderId) {
        String sql = "DELETE FROM dish_order_status_history WHERE dish_order_id = ?";
        List<Object> params = List.of(dishOrderId);

        return BaseDAO.executeUpdate(conn, sql, params);
    }
}
