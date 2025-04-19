package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.dto.MakeUpRest;
import hei.phylosius.restomanager.dto.CreateMakeUpRest;
import hei.phylosius.restomanager.model.Ingredient;
import hei.phylosius.restomanager.model.MakeUp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class MakeUpMapper {

    private IngredientMapper ingredientMapper;

    public List<MakeUp> toEntitiesWithIdOnly(List<CreateMakeUpRest> dtos){
        return dtos.stream().map(dto -> {
            MakeUp makeUp = new MakeUp();

            Ingredient ingredient = new Ingredient();
            ingredient.setId(dto.getId());
            ingredient.setName(dto.getName());

            makeUp.setIngredient(ingredient);
            makeUp.setQuantity(dto.getRequiredQuantity());

            return makeUp;
        }).toList();
    }

    public MakeUpRest toDTO(MakeUp makeUp) {
        MakeUpRest dto = new MakeUpRest();

        dto.setIngredient(
                ingredientMapper.toBasicPropertyDTO(makeUp.getIngredient())
        );
        dto.setRequiredQuantity(makeUp.getQuantity());
        dto.setUnit(makeUp.getUnit());

        return dto;
    }

    public List<MakeUpRest> toDTOs(List<MakeUp> makeUps) {
        return makeUps.stream().map(this::toDTO).toList();
    }
}
