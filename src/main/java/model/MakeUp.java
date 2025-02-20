package model;

import lombok.*;

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
}
