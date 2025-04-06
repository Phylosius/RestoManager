package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.dto.StockMovementRest;
import hei.phylosius.restomanager.model.Ingredient;
import hei.phylosius.restomanager.model.StockMovement;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class StockMovementMapper {

    public StockMovement toEntity(Ingredient affectedIngredient, StockMovementRest dto) {
        StockMovement stockMovement = new StockMovement();

        stockMovement.setAffectedIngredient(affectedIngredient);
        stockMovement.setQuantity(dto.getQuantity());
        stockMovement.setDate(dto.getDate());
        stockMovement.setType(dto.getType());

        return stockMovement;
    }

    public List<StockMovementRest> toDTOs(List<StockMovement> movs) {
        return movs.stream().map(this::toDTO).toList();
    }

    public StockMovementRest toDTO(StockMovement stockMovement) {
        StockMovementRest dto = new StockMovementRest();

        dto.setQuantity(stockMovement.getQuantity());
        dto.setDate(stockMovement.getDate());
        dto.setType(stockMovement.getType());

        return dto;
    }
}
