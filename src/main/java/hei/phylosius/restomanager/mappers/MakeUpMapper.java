package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.dto.DishIngredientRest;
import hei.phylosius.restomanager.model.Dish;
import hei.phylosius.restomanager.model.MakeUp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class MakeUpMapper {

    private final IngredientMapper ingredientMapper;

    public DishIngredientRest toDTO(MakeUp makeUp) {
        DishIngredientRest dto = new DishIngredientRest();

        dto.setIngredient(
                ingredientMapper.toBasicPropertyDTO(makeUp.getIngredient())
        );
        dto.setUnit(makeUp.getUnit());
        dto.setRequiredQuantity(makeUp.getQuantity());

        return dto;
    }

    public List<DishIngredientRest> toDTOs(List<MakeUp> makeUps) {
        return makeUps.stream().map(this::toDTO).toList();
    }
}
