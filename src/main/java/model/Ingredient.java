package model;

import dao.PriceDAO;
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

    public Price getPrice(LocalDateTime localDateTime) {
        PriceDAO priceDAO = new PriceDAO();
        return priceDAO.getNearbyByDateAndIngredientID(localDateTime, id);
    }
}
