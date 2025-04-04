package hei.phylosius.restomanager.dto;

import hei.phylosius.restomanager.model.Price;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class IngredientUpdateDTO {
    private String id;
    private String name;
    private List<Price> prices;
    private List<StockMovementDTO> stockMovements;
}
