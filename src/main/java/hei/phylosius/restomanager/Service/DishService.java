package hei.phylosius.restomanager.Service;

import hei.phylosius.restomanager.Repository.DishDAO;
import hei.phylosius.restomanager.dto.DishRest;
import hei.phylosius.restomanager.mappers.DishMapper;
import hei.phylosius.restomanager.model.Dish;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class DishService {

    private final DishDAO dishDAO;
    private final DishMapper dishMapper;

    public List<DishRest> getDishes(Integer page, Integer pageSize) {
        List<Dish> dishes;

        if (page != null && pageSize != null) {
            dishes = dishDAO.getAll(page, pageSize);
        } else {
            dishes = dishDAO.getAll();
        }

        return dishMapper.toDTOs(dishes);
    };
}

