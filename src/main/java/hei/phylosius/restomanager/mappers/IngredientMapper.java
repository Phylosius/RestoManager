package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.dto.IngredientRest;
import hei.phylosius.restomanager.model.Ingredient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class IngredientMapper {

    public List<IngredientRest> toDTOs(List<Ingredient> ingredients) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
