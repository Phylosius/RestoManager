package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.Repository.MakeUpDAO;
import hei.phylosius.restomanager.dto.DishRest;
import hei.phylosius.restomanager.model.Dish;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
@Component
public class DishMapper {

    private final IngredientMapper ingredientMapper;
    private final MakeUpMapper makeUpMapper;
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

    public List<DishRest> toDTOs(List<Dish> dishes) {
        return dishes.stream().map(this::toDTO).toList();
    }

    public DishRest toDTO(Dish dish) {
        DishRest dto = new DishRest();

        dto.setId(Integer.valueOf(dish.getId()));
        dto.setName(dish.getName());
        dto.setActualPrice(dish.getUnitPrice());
        dto.setAvailableQuantity(dish.getAvailableQuantity());
        dto.setIngredients(
                makeUpMapper.toDTOs(dish.getMakeUps())
        );

        return dto;
    }
}
