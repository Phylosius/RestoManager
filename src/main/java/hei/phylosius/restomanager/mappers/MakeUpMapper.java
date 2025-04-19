package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.Repository.IngredientDAO;
import hei.phylosius.restomanager.dto.CreateDishIngredientRest;
import hei.phylosius.restomanager.dto.DishIngredientRest;
import hei.phylosius.restomanager.model.MakeUp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class MakeUpMapper {

    private final IngredientMapper ingredientMapper;
    private final IngredientDAO ingredientDAO;

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

    public List<MakeUp> toEntities(List<CreateDishIngredientRest> createDTOs) {
        return createDTOs.stream().map(this::toEntity).toList();
    }

    private MakeUp toEntity(CreateDishIngredientRest createDTO) {
        MakeUp makeUp = new MakeUp();

        makeUp.setQuantity(createDTO.getRequiredQuantity());

        makeUp.setIngredient(
                ingredientDAO.getByName(createDTO.getName())
        );
        return makeUp;
    }


}
