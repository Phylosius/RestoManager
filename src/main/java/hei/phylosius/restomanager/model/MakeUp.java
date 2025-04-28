package hei.phylosius.restomanager.model;

import hei.phylosius.restomanager.Repository.StockMovementDAO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class MakeUp {
    private Ingredient ingredient;
    private Double quantity;

    public Unit getUnit() {
        return ingredient.getUnit();
    }

    public Boolean isMakeable(LocalDateTime date, StockMovementDAO stockMovementDAO) {
        return ingredient.getMakeableDishQuantity(quantity, date, stockMovementDAO) >= 1;
    }

    public Double getMissingIngredientQuantity(LocalDateTime date, StockMovementDAO stockMovementDAO) {
        return ingredient.getMissingQuantityForDish(quantity, date, stockMovementDAO);
    }
}
