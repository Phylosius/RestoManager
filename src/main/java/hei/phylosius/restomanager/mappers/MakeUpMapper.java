package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.dto.MakeUpRest;
import hei.phylosius.restomanager.model.MakeUp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class MakeUpMapper {

    private IngredientMapper ingredientMapper;

    public MakeUpRest toDTO(MakeUp makeUp) {
        MakeUpRest dto = new MakeUpRest();

        dto.setIngredient(
                ingredientMapper.toDTO(makeUp.getIngredient())
        );
        dto.setQuantity(makeUp.getQuantity());

        return dto;
    }

    public List<MakeUpRest> toDTOs(List<MakeUp> makeUps) {
        return makeUps.stream().map(this::toDTO).toList();
    }
}
