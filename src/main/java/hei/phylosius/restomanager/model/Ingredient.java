package hei.phylosius.restomanager.model;

import hei.phylosius.restomanager.Repository.PriceDAO;
import hei.phylosius.restomanager.Repository.StockMovementDAO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    private String id;
    private String name;
    private LocalDateTime modificationDate;
    private Price price;
    private Unit unit;

    public Price getRecentPrice(PriceDAO priceDAO) {
        return getRecentPrice(LocalDateTime.now(), priceDAO);
    }

    public Price getRecentPrice(LocalDateTime localDateTime, PriceDAO priceDAO) {
        return priceDAO.getNearbyByDateAndIngredientID(localDateTime, id);
    }

    public Double getAvailableQuantity(StockMovementDAO stockMovementDAO) {
        return getAvailableQuantity(LocalDateTime.now(), stockMovementDAO);
    }

    public int getMakeableDishQuantity(Double quantityForMakeOne, LocalDateTime date, StockMovementDAO stockMovementDAO) {
        return (int) Math.floor(getAvailableQuantity(date, stockMovementDAO) / quantityForMakeOne);
    }

    public Double getMissingQuantityForDish(Double quantityForMakingOne,  LocalDateTime date, StockMovementDAO stockMovementDAO) {
        if (getAvailableQuantity(date, stockMovementDAO) > 0) {
            return 0d;
        } else {
            return quantityForMakingOne - getAvailableQuantity(date, stockMovementDAO);
        }
    }

    public Double getAvailableQuantity(LocalDateTime date, StockMovementDAO stockMovementDAO) {
        return getAvailableQuantity(getId(), date, stockMovementDAO);
    }

    public static Double getAvailableQuantity(String ingredientId, StockMovementDAO stockMovementDAO) {
        return getStockInfo(ingredientId, LocalDateTime.now(), stockMovementDAO).getQuantity();
    }

    public static Double getAvailableQuantity(String ingredientId, LocalDateTime date, StockMovementDAO stockMovementDAO) {
        return getStockInfo(ingredientId, date, stockMovementDAO).getQuantity();
    }

    public static StockInfo getStockInfo(String ingredientId, LocalDateTime date, StockMovementDAO stockMovementDAO ) {
        return stockMovementDAO.getStockInfo(ingredientId, date);
    }
}
