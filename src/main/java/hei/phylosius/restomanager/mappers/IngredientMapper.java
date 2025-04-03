package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.dto.IngredientDTO;
import hei.phylosius.restomanager.model.Ingredient;
import org.springframework.stereotype.Component;

@Component
public class IngredientMapper {
    public IngredientDTO toDTO(Ingredient ingredient) {
        return new IngredientDTO(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getPrice().getValue(),
                ingredient.getModificationDate());
    }
}
