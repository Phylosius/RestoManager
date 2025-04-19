package hei.phylosius.restomanager.dto;

import hei.phylosius.restomanager.model.MovementType;
import hei.phylosius.restomanager.model.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockMovementRest {
    private Integer id;
    private Double quantity;
    private Unit unit;
    private MovementType type;
    private String creationDateTime;
}
