package hei.phylosius.restomanager.mappers;

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

    public StockMovement toEntity(Ingredient affectedIngredient, StockMovementRest dto) {
        StockMovement stockMovement = new StockMovement();

        stockMovement.setAffectedIngredient(affectedIngredient);
        stockMovement.setQuantity(dto.getQuantity());
        stockMovement.setDate(LocalDateTime.parse(dto.getCreationDateTime()));
        stockMovement.setType(dto.getType());

        return stockMovement;
    }

    public List<StockMovementRest> toDTOs(List<StockMovement> movs) {
        return movs.stream().map(this::toDTO).toList();
    }

    public StockMovementRest toDTO(StockMovement stockMovement) {
        StockMovementRest dto = new StockMovementRest();

        dto.setQuantity(stockMovement.getQuantity());
        dto.setCreationDateTime(stockMovement.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        dto.setType(stockMovement.getType());
        dto.setUnit(stockMovement.getUnit());
        dto.setId(
                String.format("%s.%s", stockMovement.getDate().toString(), stockMovement.getAffectedIngredient().getId())
                        .hashCode()
        );

        return dto;
    }

    public List<StockMovement> toEntities(Ingredient byId, List<StockMovementRest> movements) {
        return movements.stream().map(m -> toEntity(byId, m)).toList();
    }
}
