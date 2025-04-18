package hei.phylosius.restomanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishRest {
    private Integer id;
    private String name;
    private Integer availableQuantity;
    private Double actualPrice;
    private List<DishIngredientRest> ingredients;
}
