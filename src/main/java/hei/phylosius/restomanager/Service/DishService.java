package hei.phylosius.restomanager.Service;

import hei.phylosius.restomanager.Repository.DishDAO;
import hei.phylosius.restomanager.Repository.MakeUpDAO;
import hei.phylosius.restomanager.dto.DishRest;
import hei.phylosius.restomanager.dto.MakeUpRestCreation;
import hei.phylosius.restomanager.mappers.DishMapper;
import hei.phylosius.restomanager.mappers.MakeUpMapper;
import hei.phylosius.restomanager.model.Dish;
import hei.phylosius.restomanager.model.Ingredient;
import hei.phylosius.restomanager.model.MakeUp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class DishService {

    private DishDAO dishDAO;
    private DishMapper dishMapper;
    private MakeUpDAO makeUpDAO;
    private MakeUpMapper makeUpMapper;

    public List<DishRest> getDishRests() {
        List<Dish> dishes = dishDAO.getAll();

        return dishMapper.toDTOsWithStock(dishes);
    }

    public List<MakeUpRestCreation> addIngredients(String dishId, List<MakeUpRestCreation> makeUpRests) {
        List<MakeUp> makeUps = makeUpMapper.toEntitiesWithIdOnly(makeUpRests);

        makeUpDAO.saveAll(dishId, makeUps);

        return makeUpRests;
    }
}
