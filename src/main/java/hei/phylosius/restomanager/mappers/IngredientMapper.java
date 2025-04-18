package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.Repository.PriceDAO;
import hei.phylosius.restomanager.Repository.StockMovementDAO;
import hei.phylosius.restomanager.dto.IngredientRest;
import hei.phylosius.restomanager.model.Ingredient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class IngredientMapper {

    private final StockMovementDAO stockMovementDAO;
    private final StockMovementMapper stockMovementMapper;
    private final PriceMapper priceMapper;
    private final PriceDAO priceDAO;

    public List<IngredientRest> toDTOs(List<Ingredient> ingredients) {
        return ingredients.stream().map(this::toDTO).toList();
    }

    public IngredientRest toDTO(Ingredient ingredient) {
        IngredientRest ingredientRest = new IngredientRest();
        String ingredientId = ingredient.getId();

        ingredientRest.setId(Integer.valueOf(ingredientId));
        ingredientRest.setName(ingredient.getName());
        ingredientRest.setAvailableQuantity(ingredient.getAvailableQuantity());
        ingredientRest.setActualPrice(ingredient.getActualPrice().getValue());
        ingredientRest.setStockMovements(
                stockMovementMapper.toDTOs(
                        stockMovementDAO.getAllByIngredientID(ingredientId)
                )
        );
        ingredientRest.setPrices(
                priceMapper.toDTOs(
                        priceDAO.getAllByIngredientID(ingredientId)
                )
        );

        return ingredientRest;
    }
}
