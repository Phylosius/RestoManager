package hei.phylosius.restomanager.Service;

import hei.phylosius.restomanager.Repository.IngredientDAO;
import hei.phylosius.restomanager.dto.IngredientRest;
import hei.phylosius.restomanager.mappers.IngredientMapper;
import hei.phylosius.restomanager.model.Ingredient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class IngredientService {

    private IngredientDAO ingredientDAO;
    private IngredientMapper ingredientMapper;

    public List<IngredientRest> getAllIngredients(Integer page, Integer pageSize) {
        List<Ingredient> ingredients;

        if (page != null && pageSize != null) {
            ingredients = ingredientDAO.getAll(page, pageSize);
        } else {
            ingredients = ingredientDAO.getAll();
        }

        return ingredientMapper.toDTOs(ingredients);
    }

}
