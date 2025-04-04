package hei.phylosius.restomanager.dto;

import hei.phylosius.restomanager.model.MovementType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class StockMovementDTO {
    private MovementType type;
    private Double quantity;
    private LocalDateTime date;
}
