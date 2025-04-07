package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.Repository.MakeUpDAO;
import hei.phylosius.restomanager.dto.DishRest;
import hei.phylosius.restomanager.dto.MakeUpRest;
import hei.phylosius.restomanager.model.Dish;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
@Component
public class DishMapper {

    private MakeUpDAO makeUpDAO;
    private MakeUpMapper makeUpMapper;

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

    public List<DishRest> toDTOsWithStock(List<Dish> dishes) {
        return dishes.stream().map(this::toDTOWithStock).toList();
    }

    public List<DishRest> toDTOs(List<Dish> dishes) {
        return dishes.stream().map(this::toDTO).toList();
    }

    public DishRest toDTOWithStock(Dish dish) {
        DishRest dishRest = new DishRest();
        String dishId = dish.getId();

        dishRest.setId(dishId);
        dishRest.setIngredients(
                makeUpMapper.toDTOsWithStock(dish.getMakeUps())
        );
        dishRest.setAvailableQuantity(dish.getAvailableQuantity());

        return dishRest;
    }

    public DishRest toDTO(Dish dish) {
        DishRest dishRest = new DishRest();
        String dishId = dish.getId();

        dishRest.setId(dishId);
        dishRest.setIngredients(
            makeUpMapper.toDTOs(dish.getMakeUps())
        );
        dishRest.setAvailableQuantity(dish.getAvailableQuantity());

        return dishRest;
    }
}
