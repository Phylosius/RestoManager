package hei.phylosius.restomanager.dto;

import hei.phylosius.restomanager.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateDishOrderStatus {
    private OrderStatus status;
}
