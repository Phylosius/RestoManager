package hei.phylosius.restomanager.Service;

import hei.phylosius.restomanager.Repository.IngredientDAO;
import hei.phylosius.restomanager.Repository.IngredientNotFoundException;
import hei.phylosius.restomanager.Repository.PriceDAO;
import hei.phylosius.restomanager.dto.IngredientRest;
import hei.phylosius.restomanager.dto.PriceRest;
import hei.phylosius.restomanager.mappers.IngredientMapper;
import hei.phylosius.restomanager.mappers.PriceMapper;
import hei.phylosius.restomanager.model.Ingredient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class IngredientService {

    private IngredientDAO ingredientDAO;
    private IngredientMapper ingredientMapper;
    private PriceDAO priceDAO;
    private PriceMapper priceMapper;

    public List<IngredientRest> getAllIngredients(Integer page, Integer pageSize) {
        List<Ingredient> ingredients;

        if (page != null && pageSize != null) {
            ingredients = ingredientDAO.getAll(page, pageSize);
        } else {
            ingredients = ingredientDAO.getAll();
        }

        return ingredientMapper.toDTOs(ingredients);
    }

    public IngredientRest getIngredientById(Integer id) {
        Ingredient ingredient;

        if (id == null) {
            throw new NullIdException("Can not found Ingredient while id is null");
        } else {
             ingredient = ingredientDAO.getById(id.toString());
        }

        if (ingredient == null) {
            throw new IngredientNotFoundException(String.format("Ingredient of id = %s not found", id));
        }

        return ingredientMapper.toDTO(ingredient);
    }

    public IngredientRest addPrices(Integer ingredientId, List<PriceRest> prices) {
        if (ingredientId == null) {
            throw new NullIdException("Given an null value for ingredient's id.");
        }

        if (!prices.isEmpty()) {
            priceDAO.addAllByIngredientId(
                    ingredientId.toString(),
                    priceMapper.toEntities(prices)
            );
        }

        return getIngredientById(ingredientId);
    }

}
