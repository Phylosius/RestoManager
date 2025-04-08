package hei.phylosius.restomanager.dto;

import hei.phylosius.restomanager.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishOrderRest {
    private String name;
    private Double unitPrice;
    private int quantity;
    private OrderStatus status;
}
