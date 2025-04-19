package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.dto.OrderRest;
import hei.phylosius.restomanager.model.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class OrderMapper {

    private DishOrderMapper dishOrderMapper;

    public OrderRest toDTO(Order order) {
        OrderRest orderRest = new OrderRest();

        orderRest.setId(Integer.parseInt(order.getId()));
        orderRest.setTotalAmount(order.getTotalAmount());
        orderRest.setDishes(
                dishOrderMapper.toDTOs(order.getDishOrders())
        );
        orderRest.setActualStatus(order.getActualStatus());

        return orderRest;
    }
}
