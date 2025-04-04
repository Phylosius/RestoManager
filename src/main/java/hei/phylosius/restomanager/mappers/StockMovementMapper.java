package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.dto.StockMovementDTO;
import hei.phylosius.restomanager.model.Ingredient;
import hei.phylosius.restomanager.model.StockMovement;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

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
}
