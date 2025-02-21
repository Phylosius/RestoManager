package model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Dish {
    private String id;
    private String name;
    private Double unitPrice;
    private List<MakeUp> ingredients;

    public Double getProductionCost(){
        return ingredients.stream()
                .map(m-> m.getIngredient().getUnitPrice() * m.getQuantity())
                .reduce(0.0, Double::sum);
    }
}
