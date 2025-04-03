package hei.phylosius.restomanager.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public List<MakeUp> getMissingIngredients(LocalDateTime date) {
        List<MakeUp> missingIngredients = new ArrayList<>();

        makeUps.forEach(makeUp -> {
            if  (makeUp.isMakeable(date)) {
                MakeUp missingMakeUp = new MakeUp();

                missingMakeUp.setIngredient(makeUp.getIngredient());
                missingMakeUp.setQuantity(makeUp.getMissingIngredientQuantity(date));

                missingIngredients.add(makeUp);
            }
        });

        return missingIngredients;
    }

    public int getAvailableQuantity() {
        return getAvailableQuantity(LocalDateTime.now());
    }

    public int getAvailableQuantity(LocalDateTime date) {
        MakeUp firstMakeUp = makeUps.getFirst();
        int minimalMakeableQuantity = getMakeableQuantityByMakeUp(firstMakeUp, date);

        for (int i = 1; i < makeUps.size(); i++) {
            MakeUp makeUp = makeUps.get(i);
            int makeableQuantity = getMakeableQuantityByMakeUp(makeUp, date);

            minimalMakeableQuantity = Math.min(minimalMakeableQuantity, makeableQuantity);
        }

        return minimalMakeableQuantity;
    }

    public int getMakeableQuantityByMakeUp(MakeUp makeUp, LocalDateTime date) {
        return makeUp.getIngredient()
                .getMakeableDishQuantity(makeUp.getQuantity(), date);
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
