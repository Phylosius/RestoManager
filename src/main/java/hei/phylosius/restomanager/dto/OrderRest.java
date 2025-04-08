package hei.phylosius.restomanager.dto;

import hei.phylosius.restomanager.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRest {
    private String id;
    private OrderStatus status;
    private List<DishOrderRest> dishes;
}
