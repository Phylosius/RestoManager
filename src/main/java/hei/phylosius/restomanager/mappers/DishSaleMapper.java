package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.dto.DishSaleRest;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DishSaleMapper {
    public DishSaleRest toDTO(ResultSet resultSet) throws SQLException {
        DishSaleRest dishSaleRest = new DishSaleRest();

        dishSaleRest.setDishName(resultSet.getString("dish_name"));
        dishSaleRest.setSailedQuantity(resultSet.getInt("sailed_quantity"));
        dishSaleRest.setTotalGain(resultSet.getDouble("total_gain"));

        return dishSaleRest;
    }
}
