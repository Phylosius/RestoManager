package hei.phylosius.restomanager.dto;

import java.util.List;

import hei.phylosius.restomanager.dto.DishIngredientRest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishRest {
    private String id;
    private List<DishIngredientRest> ingredients;
    private int avalaibleQuantity;
}
