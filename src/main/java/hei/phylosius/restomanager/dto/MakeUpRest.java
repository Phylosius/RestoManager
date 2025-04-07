package hei.phylosius.restomanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MakeUpRest {
    private IngredientRest ingredient;
    private Double quantity;

}
