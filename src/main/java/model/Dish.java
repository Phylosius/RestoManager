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
    private List<MakeUp> makeUps;

    public Double getProductionCost(){
        return makeUps.stream()
                .map(m-> m.getIngredient().getPrice().getValue() * m.getQuantity())
                .reduce(0.0, Double::sum);
    }

    public Double getGrossMargin() {
        return getUnitPrice() - getProductionCost();
    }
}
