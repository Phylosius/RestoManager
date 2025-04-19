package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.Service.NullIdException;
import hei.phylosius.restomanager.dto.StockMovementRest;
import hei.phylosius.restomanager.model.Ingredient;
import hei.phylosius.restomanager.model.StockMovement;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
@Component
public class StockMovementMapper {
    public List<StockMovementRest> toDTOs(List<StockMovement> movements) {
        return movements.stream().map(this::toDTO).toList();
    }

    public StockMovementRest toDTO(StockMovement movement) {

        StockMovementRest dto = new StockMovementRest();

        String preId = String.format("%s.%s", movement.getDate(), movement.getAffectedIngredient().getId());
        dto.setId(preId.hashCode());
        dto.setUnit(movement.getUnit());
        dto.setQuantity(movement.getQuantity());
        dto.setType(movement.getType());
        dto.setCreationDateTime(
                movement.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );


        return dto;
    }

    public List<StockMovement> toEntities(Integer ingredientId, List<StockMovementRest> DTOs) {
        return DTOs.stream().map(dto -> toEntity(ingredientId, dto)).toList();
    }

    public StockMovement toEntity(Integer ingredientId, StockMovementRest dto) {

//        if (ingredientId == null) {
//            throw new NullIdException("Can't convert dto without ingredient's id");
//        }
        StockMovement movement = new StockMovement();

        movement.setType(dto.getType());
        movement.setQuantity(dto.getQuantity());
        movement.setDate(LocalDateTime.parse(dto.getCreationDateTime()));

        Ingredient affectedIngredient = new Ingredient();
        affectedIngredient.setId(ingredientId.toString());
        movement.setAffectedIngredient(affectedIngredient);

        return movement;
    }
}
