package hei.phylosius.restomanager.Service;

import hei.phylosius.restomanager.Repository.IngredientDAO;
import hei.phylosius.restomanager.dto.IngredientRest;
import hei.phylosius.restomanager.mappers.IngredientMapper;
import hei.phylosius.restomanager.model.Ingredient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class IngredientService {

    private final IngredientDAO ingredientDAO;
    private final IngredientMapper ingredientMapper;

    public Boolean isExist(Integer id) {
        return ingredientDAO.isExist(id.toString());
    }

    public IngredientRest getOneIngredient(Integer id) {
        Ingredient ingredient = ingredientDAO.getById(id.toString());
        return ingredientMapper.toDTO(ingredient);
    }

    public List<IngredientRest> getIngredients(Integer page, Integer pageSize){

        if (page != null && pageSize != null) {
            return ingredientMapper.toDTOs(ingredientDAO.getAll(page, pageSize));
        } else {
            return ingredientMapper.toDTOs(ingredientDAO.getAll());
        }
    }


}
