package hei.phylosius.restomanager.dto;

import hei.phylosius.restomanager.model.Price;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientRestDetailled {
    private String id;
    private String name;
    private Double avalaibleQuantity;
    private Double currentPrice;
    private List<Price> priceHistory;
    private List<StockMovementRest> movementHistory;
}
