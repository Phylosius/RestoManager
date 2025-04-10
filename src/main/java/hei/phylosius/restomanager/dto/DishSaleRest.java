package hei.phylosius.restomanager.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DishSaleRest {
    private String dishName;
    private int sailedQuantity;
    private Double totalGain;
}
