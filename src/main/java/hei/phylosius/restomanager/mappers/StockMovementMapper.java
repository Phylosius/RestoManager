package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.dto.StockMovementDTO;
import hei.phylosius.restomanager.model.Ingredient;
import hei.phylosius.restomanager.model.StockMovement;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class StockMovementMapper {

    public StockMovement toEntity(Ingredient affectedIngredient, StockMovementDTO dto) {
        StockMovement stockMovement = new StockMovement();

        stockMovement.setAffectedIngredient(affectedIngredient);
        stockMovement.setQuantity(dto.getQuantity());
        stockMovement.setDate(dto.getDate());
        stockMovement.setType(dto.getType());

        return stockMovement;
    }

    public List<StockMovementDTO> toDTOs(List<StockMovement> movs) {
        return movs.stream().map(this::toDTO).toList();
    }

    public StockMovementDTO toDTO(StockMovement stockMovement) {
        StockMovementDTO dto = new StockMovementDTO();

        dto.setQuantity(stockMovement.getQuantity());
        dto.setDate(stockMovement.getDate());
        dto.setType(stockMovement.getType());

        return dto;
    }
}
