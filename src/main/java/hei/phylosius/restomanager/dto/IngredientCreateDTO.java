package hei.phylosius.restomanager.dto;

import hei.phylosius.restomanager.model.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class IngredientCreateDTO {
    private String name;
    private Unit unit;
}
