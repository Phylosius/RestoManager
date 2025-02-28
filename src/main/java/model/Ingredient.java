package model;

import dao.IngredientDAO;
import dao.PriceDAO;
import dao.StockMovementDAO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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

    public Price getPrice() {
        return getPrice(LocalDateTime.now());
    }

    public Price getPrice(LocalDateTime localDateTime) {
        PriceDAO priceDAO = new PriceDAO();
        return priceDAO.getNearbyByDateAndIngredientID(localDateTime, id);
    }

    public Double getAvalaibleQuantity() {
        return getAvalaibleQuantity(LocalDateTime.now());
    }

    public Double getAvalaibleQuantity(LocalDateTime date) {
        return getAvalaibleQuantity(getId(), date);
    }

    public static Double getAvalaibleQuantity(String ingredientId, LocalDateTime date) {
        return getStockInfo(ingredientId, date).getQuantity();
    }

    public static StockInfo getStockInfo(String ingredientId, LocalDateTime date) {
        StockMovementDAO stockMovementDAO = new StockMovementDAO();
        return stockMovementDAO.getStockInfo(ingredientId, date);
    }
}
