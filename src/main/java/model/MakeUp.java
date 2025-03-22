package model;

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

    public Double getMissingIngredientQuantity(LocalDateTime date) {
        return ingredient.getMissingQuantityForDish(quantity, date);
    }
}
