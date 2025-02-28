package model;

import lombok.*;

import java.time.LocalDateTime;
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
        return getProductionCost(LocalDateTime.now());
    }

    public Double getGrossMargin() {
        return getGrossMargin(LocalDateTime.now());
    }

    public Double getProductionCost(LocalDateTime date){
        return makeUps.stream()
                .map(m-> m.getIngredient().getRecentPrice(date).getValue() * m.getQuantity())
                .reduce(0.0, Double::sum);
    }

    public Double getGrossMargin(LocalDateTime date) {
        return getUnitPrice() - getProductionCost(date);
    }
}
