package hei.phylosius.restomanager.Repository;

import hei.phylosius.restomanager.dto.DishSaleRest;
import hei.phylosius.restomanager.mappers.DishSaleMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Repository
public class DishSaleDAO {

    private DataSource dataSource;
    private DishSaleMapper dishSaleMapper;

    public List<DishSaleRest> getBestSales(Integer limit, LocalDateTime startDate, LocalDateTime endDate) {
        List<DishSaleRest> dishSaleRestList = new ArrayList<>();

        String dateCondition = "";
        String limitCondition = "";
        List<Object> params = new ArrayList<>();


        if (startDate != null && endDate != null) {
            dateCondition = """
                    WHERE
                        o.created_at >= ?::TIMESTAMP
                    AND o.created_at <= ?::TIMESTAMP
                    
                    """;
            params.addAll(List.of(startDate, endDate));
        } else if (startDate != null) {
            dateCondition = """
                    WHERE
                        o.created_at >= ?::TIMESTAMP
                    
                    """;
            params.add(startDate);
        } else if (endDate != null) {
            dateCondition = """
                    WHERE
                        o.created_at <= ?::TIMESTAMP
                    
                    """;
            params.add(endDate);
        }

        if (limit != null && limit > 0) {
            limitCondition = """
                    LIMIT ?::INTEGER
                    """;
            params.add(limit);
        }

        String sql = String.format("""
                SELECT
                    di.name as dish_name,
                    SUM(di_o.quantity) as sailed_quantity,
                    SUM(di.unit_price * di_o.quantity) as total_gain
                
                FROM
                    dish_order di_o
                        JOIN "order" o
                             ON di_o.order_id = o.id
                        JOIN dish di
                             ON di_o.dish_id = di.id
                        JOIN dish_order_status_history di_o_sth
                             ON (di_o.id = di_o_sth.dish_order_id
                                 AND di_o_sth.status_id='FINISHED')
                
                %s
                
                GROUP BY di.name
                
                ORDER BY sailed_quantity DESC
                
                %s
                """, dateCondition, limitCondition);

        BaseDAO.executeQuery(dataSource.getConnection(), sql, params, resultSet -> {
            while (resultSet.next()) {
                dishSaleRestList.add(dishSaleMapper.toDTO(resultSet));
            }
        });

        return dishSaleRestList;
    }
}
