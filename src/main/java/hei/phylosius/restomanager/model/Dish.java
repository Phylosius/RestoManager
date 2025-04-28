package hei.phylosius.restomanager.model;

import hei.phylosius.restomanager.Repository.PriceDAO;
import hei.phylosius.restomanager.Repository.StockMovementDAO;
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

    public Double getProductionCost(PriceDAO priceDAO) {
        return getProductionCost(LocalDateTime.now(), priceDAO);
    }

    public Double getGrossMargin(PriceDAO priceDAO) {
        return getGrossMargin(LocalDateTime.now(), priceDAO);
    }

    public List<MakeUp> getMissingIngredients(LocalDateTime date, StockMovementDAO stockMovementDAO) {
        List<MakeUp> missingIngredients = new ArrayList<>();

        makeUps.forEach(makeUp -> {
            if  (makeUp.isMakeable(date, stockMovementDAO)) {
                MakeUp missingMakeUp = new MakeUp();

                missingMakeUp.setIngredient(makeUp.getIngredient());
                missingMakeUp.setQuantity(makeUp.getMissingIngredientQuantity(date, stockMovementDAO));

                missingIngredients.add(makeUp);
            }
        });

        return missingIngredients;
    }

    public int getAvailableQuantity(StockMovementDAO stockMovementDAO) {
        return getAvailableQuantity(LocalDateTime.now(), stockMovementDAO);
    }

    public int getAvailableQuantity(LocalDateTime date, StockMovementDAO stockMovementDAO) {
        MakeUp firstMakeUp = makeUps.getFirst();
        int minimalMakeableQuantity = getMakeableQuantityByMakeUp(firstMakeUp, date, stockMovementDAO);

        for (int i = 1; i < makeUps.size(); i++) {
            MakeUp makeUp = makeUps.get(i);
            int makeableQuantity = getMakeableQuantityByMakeUp(makeUp, date, stockMovementDAO);

            minimalMakeableQuantity = Math.min(minimalMakeableQuantity, makeableQuantity);
        }

        return minimalMakeableQuantity;
    }

    public int getMakeableQuantityByMakeUp(MakeUp makeUp, LocalDateTime date, StockMovementDAO stockMovementDAO) {
        return makeUp.getIngredient()
                .getMakeableDishQuantity(makeUp.getQuantity(), date, stockMovementDAO);
    }

    public Double getProductionCost(LocalDateTime date, PriceDAO priceDAO){
        return makeUps.stream()
                .map(m-> m.getIngredient().getRecentPrice(date, priceDAO).getValue() * m.getQuantity())
                .reduce(0.0, Double::sum);
    }

    public Double getGrossMargin(LocalDateTime date, PriceDAO priceDAO) {
        return getUnitPrice() - getProductionCost(date, priceDAO);
    }
}
