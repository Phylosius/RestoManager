package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.dto.OrderRest;
import hei.phylosius.restomanager.dto.OrderRestGet;
import hei.phylosius.restomanager.model.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

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

    public OrderRestGet toDTOGet(Order order) {
        OrderRestGet orderRestGet = new OrderRestGet();

        orderRestGet.setId(order.getId());
        orderRestGet.setCreationDate(order.getCreationDate());
        orderRestGet.setReference(order.getReference());

        return orderRestGet;
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

    public List<OrderRest> toDTOs(List<Order> all) {
        return all.stream().map(this::toDTO).toList();
    }

    public List<OrderRestGet> toDTOsGet(List<Order> all) {
        return all.stream().map(this::toDTOGet).toList();
    }
}
