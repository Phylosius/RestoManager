package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.Repository.MakeUpDAO;
import hei.phylosius.restomanager.model.Dish;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
@Component
public class DishMapper {

    private MakeUpDAO makeUpDAO;

    public Dish toEntity(ResultSet resultSet) throws SQLException {
        Dish dish = new Dish();
        String dishId = resultSet.getString("id");

        dish.setId(dishId);
        dish.setName(resultSet.getString("name"));
        dish.setUnitPrice(resultSet.getDouble("unit_price"));
        dish.setMakeUps(
                makeUpDAO.getAllByDishID(dishId)
        );

        return dish;
    }
}
