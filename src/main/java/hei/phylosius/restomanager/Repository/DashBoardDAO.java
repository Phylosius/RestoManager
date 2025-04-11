package hei.phylosius.restomanager.Repository;

import hei.phylosius.restomanager.dto.DishProcessingTimeRest;
import hei.phylosius.restomanager.dto.DishSaleRest;
import hei.phylosius.restomanager.dto.ProcessingTimeType;
import hei.phylosius.restomanager.mappers.DishProcessingTimeMapper;
import hei.phylosius.restomanager.mappers.DishSaleMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
@Repository
public class DashBoardDAO {

    private DataSource dataSource;
    private DishSaleMapper dishSaleMapper;
    private DishProcessingTimeMapper dishProcessingTimeMapper;

    public DishProcessingTimeRest getProcessingTime(String dishId, ProcessingTimeType processingTimeType, String processingTimeFormat, LocalDateTime startDate, LocalDateTime endDate) {
        if (!DishDAO.isExist(dataSource.getConnection(), dishId)) {
            throw new DishNotFoundException(String.format("Dish of id %s not found", dishId));
        }

        AtomicReference<DishProcessingTimeRest> dishProcessingTimeRest = new AtomicReference<>();

        String processingTimeSQL = switch (processingTimeType) {
            case ProcessingTimeType.MAXIMUM -> "MAX";
            case ProcessingTimeType.AVERAGE -> "AVG";
            case ProcessingTimeType.MINIMUM -> "MIN";
            case null -> "AVG";
        };

        List<Object> params = new ArrayList<>(List.of(dishId));

        String dateCondition = "";

        if (startDate != null && endDate != null) {
            dateCondition = """
                    AND
                        "do_sh".date >= ?::TIMESTAMP
                    AND "do_sh".date <= ?::TIMESTAMP
                    
                    """;
            params.addAll(List.of(startDate, endDate));
        } else if (startDate != null) {
            dateCondition = """
                    AND
                        "do_sh".date >= ?::TIMESTAMP
                    
                    """;
            params.add(startDate);
        } else if (endDate != null) {
            dateCondition = """
                    AND
                        "do_sh".date <= ?::TIMESTAMP
                    
                    """;
            params.add(endDate);
        }

        String sql = String.format("""
                SELECT
                    dish_id,
                    %s(EXTRACT(EPOCH FROM(processing_time))) as processing_time
                FROM
                (
                    SELECT
                        "do".dish_id as dish_id,
                        ("do_sh_".date - "do_sh".date) as processing_time
                    FROM
                        dish_order_status_history "do_sh"
                            JOIN dish_order "do"
                                 ON "do_sh".dish_order_id = "do".id
                            JOIN dish_order_status_history "do_sh_"
                                 ON "do_sh".dish_order_id = "do_sh_".dish_order_id
                
                    WHERE
                        "do".dish_id = ?
                      AND "do_sh".status_id = 'IN_PREPARATION'
                      AND "do_sh_".status_id = 'FINISHED'
                      %s
                ) as dif
                GROUP BY dish_id
                
                """, processingTimeSQL, dateCondition);

        BaseDAO.executeQuery(dataSource.getConnection(), sql, params, resultSet -> {
            if (resultSet.next()) {
                dishProcessingTimeRest.set(dishProcessingTimeMapper.toDTO(resultSet, processingTimeType, processingTimeFormat));
            }
        });

        return dishProcessingTimeRest.get();
    }

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
