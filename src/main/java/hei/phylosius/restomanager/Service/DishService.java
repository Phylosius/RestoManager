package hei.phylosius.restomanager.Service;

import hei.phylosius.restomanager.Repository.DishDAO;
import hei.phylosius.restomanager.Repository.MakeUpDAO;
import hei.phylosius.restomanager.dto.DishRest;
import hei.phylosius.restomanager.dto.CreateMakeUpRest;
import hei.phylosius.restomanager.mappers.DishMapper;
import hei.phylosius.restomanager.mappers.MakeUpMapper;
import hei.phylosius.restomanager.model.Dish;
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

    public List<DishRest> getDishRests(Integer page, Integer pageSize) {
        List<Dish> dishes = dishDAO.getAll(page, pageSize);

        return dishMapper.toDTOs(dishes);
    }

    public List<DishRest> getDishRests() {
        List<Dish> dishes = dishDAO.getAll();

        return dishMapper.toDTOs(dishes);
    }

    public DishRest getOneDishRest(Integer id) {
        return dishMapper.toDTO(dishDAO.getById(id.toString()));
    }

    public void saveIngredients(Integer dishId, List<CreateMakeUpRest> makeUpRests) {
        List<MakeUp> makeUps = makeUpMapper.toEntitiesWithIdOnly(makeUpRests);

        makeUpDAO.saveAll(dishId.toString(), makeUps);
    }
}
