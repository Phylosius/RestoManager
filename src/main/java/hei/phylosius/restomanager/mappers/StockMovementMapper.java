package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.dto.StockMovementRest;
import hei.phylosius.restomanager.model.StockMovement;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

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
}
