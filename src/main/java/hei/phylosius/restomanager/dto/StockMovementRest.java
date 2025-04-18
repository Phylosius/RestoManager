package hei.phylosius.restomanager.dto;

import hei.phylosius.restomanager.model.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockMovementRest {
    private Integer id;
    private Double quantity;
    private Unit unit;
    private StockMovementType type;
    private String creationDateTime;
}
