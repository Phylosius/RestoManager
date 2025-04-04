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

    public Price getRecentPrice() {
        return getRecentPrice(LocalDateTime.now());
    }

    public Price getRecentPrice(LocalDateTime localDateTime) {
        PriceDAO priceDAO = new PriceDAO();
        return priceDAO.getNearbyByDateAndIngredientID(localDateTime, id);
    }

    public Double getAvailableQuantity() {
        return getAvailableQuantity(LocalDateTime.now());
    }

    public int getMakeableDishQuantity(Double quantityForMakeOne, LocalDateTime date) {
        return (int) Math.floor(getAvailableQuantity(date) / quantityForMakeOne);
    }

    public Double getMissingQuantityForDish(Double quantityForMakingOne,  LocalDateTime date) {
        if (getAvailableQuantity(date) > 0) {
            return 0d;
        } else {
            return quantityForMakingOne - getAvailableQuantity(date);
        }
    }

    public Double getAvailableQuantity(LocalDateTime date) {
        return getAvailableQuantity(getId(), date);
    }

    public static Double getAvailableQuantity(String ingredientId) {
        return getStockInfo(ingredientId, LocalDateTime.now()).getQuantity();
    }

    public static Double getAvailableQuantity(String ingredientId, LocalDateTime date) {
        return getStockInfo(ingredientId, date).getQuantity();
    }

    public static StockInfo getStockInfo(String ingredientId, LocalDateTime date) {
        StockMovementDAO stockMovementDAO = new StockMovementDAO();
        return stockMovementDAO.getStockInfo(ingredientId, date);
    }
}
