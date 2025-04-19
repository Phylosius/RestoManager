package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.dto.OrderRest;
import hei.phylosius.restomanager.model.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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

    public Order toEntity(String reference, OrderRest dto) {
        Order order = new Order();

        order.setId(dto.getId().toString());
        order.setReference(reference);
        order.setCreationDate(LocalDateTime.now());
        order.setDishOrders(
                dishOrderMapper.toEntities(dto.getId().toString(), dto.getDishes())
        );

        return order;
    }
}
