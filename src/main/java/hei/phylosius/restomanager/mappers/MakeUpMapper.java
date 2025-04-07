package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.dto.MakeUpRest;
import hei.phylosius.restomanager.dto.MakeUpRestCreation;
import hei.phylosius.restomanager.model.Ingredient;
import hei.phylosius.restomanager.model.MakeUp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class MakeUpMapper {

    private IngredientMapper ingredientMapper;

    public List<MakeUp> toEntitiesWithIdOnly(List<MakeUpRestCreation> dtos){
        return dtos.stream().map(dto -> {
            MakeUp makeUp = new MakeUp();

            Ingredient ingredient = new Ingredient();
            ingredient.setId(dto.getIngredientId());

            makeUp.setIngredient(ingredient);
            makeUp.setQuantity(dto.getQuantity());

            return makeUp;
        }).toList();
    }

    public MakeUpRest toDTO(MakeUp makeUp) {
        MakeUpRest dto = new MakeUpRest();

        dto.setIngredient(
                ingredientMapper.toDTO(makeUp.getIngredient())
        );
        dto.setQuantity(makeUp.getQuantity());

        return dto;
    }

    public MakeUpRest toDTOWithStock(MakeUp makeUp) {
        MakeUpRest dto = new MakeUpRest();

        dto.setIngredient(
                ingredientMapper.toDTOWithStock(makeUp.getIngredient())
        );
        dto.setQuantity(makeUp.getQuantity());

        return dto;
    }

    public List<MakeUpRest> toDTOsWithStock(List<MakeUp> makeUps) {
        return makeUps.stream().map(this::toDTOWithStock).toList();
    }

    public List<MakeUpRest> toDTOs(List<MakeUp> makeUps) {
        return makeUps.stream().map(this::toDTO).toList();
    }
}
