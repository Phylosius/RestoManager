package hei.phylosius.restomanager.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class StockMovement {
    private Ingredient affectedIngredient;
    private MovementType type;
    private Double quantity;
    private LocalDateTime date;

    public Unit getUnit() {
        return affectedIngredient.getUnit();
    }
}
