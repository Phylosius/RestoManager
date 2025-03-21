package dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.Criteria;
import model.CriteriaOperator;
import model.LogicalOperator;
import model.OrderStatusRecord;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusRecordDAO{

    private DataSource dataSource;

    public List<OrderStatusRecord> getAllByCriteria(List<Criteria> criteria, int page, int pageSize) {
        return getAllByCriteria(dataSource.getConnection(), criteria, page, pageSize);
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
}
