package hei.phylosius.restomanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientRest extends IngredientBasicPropertyRest{
    private Double availableQuantity;
    private Double actualPrice;
    private List<PriceRest> prices;
    private List<StockMovementRest> stockMovements;
}
