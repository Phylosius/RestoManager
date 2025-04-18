package hei.phylosius.restomanager.dto;

import hei.phylosius.restomanager.model.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishIngredientRest {
    private Double requiredQuantity;
    private Unit unit;
    private IngredientBasicPropertyRest ingredient;
}
