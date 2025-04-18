package hei.phylosius.restomanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientRest extends IngredientBasicPropertyRest {
    private Double availableQuantity;
    private Double actualPrice;
    private List<PriceRest> prices;
    private List<StockMovementRest> stockMovements;
}
