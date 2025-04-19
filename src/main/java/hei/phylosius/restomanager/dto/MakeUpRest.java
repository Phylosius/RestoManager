package hei.phylosius.restomanager.dto;

import hei.phylosius.restomanager.model.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MakeUpRest {
    private Double requiredQuantity;
    private Unit unit;
    private IngredientBasicPropertyRest ingredient;

}
