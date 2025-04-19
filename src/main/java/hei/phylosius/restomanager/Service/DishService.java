package hei.phylosius.restomanager.Service;

import hei.phylosius.restomanager.Repository.DishDAO;
import hei.phylosius.restomanager.Repository.MakeUpDAO;
import hei.phylosius.restomanager.dto.CreateDishIngredientRest;
import hei.phylosius.restomanager.dto.DishRest;
import hei.phylosius.restomanager.mappers.DishMapper;
import hei.phylosius.restomanager.mappers.MakeUpMapper;
import hei.phylosius.restomanager.model.Dish;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class DishService {

    private final DishDAO dishDAO;
    private final DishMapper dishMapper;
    private final MakeUpDAO makeUpDAO;
    private final MakeUpMapper makeUpMapper;

    public List<DishRest> getDishes(Integer page, Integer pageSize) {
        List<Dish> dishes;

        if (page != null && pageSize != null) {
            dishes = dishDAO.getAll(page, pageSize);
        } else {
            dishes = dishDAO.getAll();
        }

        return dishMapper.toDTOs(dishes);
    }

    public DishRest updateIngredients(Integer dishId, List<CreateDishIngredientRest> ingredients) {

        if (dishId == null) {
            throw new NullIdException("Dish id cannot be null");
        }

        if (ingredients != null && !ingredients.isEmpty()) {
            makeUpDAO.addAll(dishId.toString(), makeUpMapper.toEntities(ingredients));
        }
        return dishMapper.toDTO(dishDAO.getById(dishId.toString()));
    }
}

