package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.Repository.DishOrderDAO;
import hei.phylosius.restomanager.Repository.OrderStatusRecordDAO;
import hei.phylosius.restomanager.dto.DishOrderRest;
import hei.phylosius.restomanager.dto.DishOrderRestUpdate;
import hei.phylosius.restomanager.model.Dish;
import hei.phylosius.restomanager.model.DishOrder;
import hei.phylosius.restomanager.model.OrderStatusHistory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Component
public class DishOrderMapper {

    private DishOrderDAO dishOrderDAO;
    private OrderStatusRecordDAO orderStatusRecordDAO;

    public List<DishOrder> toEntities(String orderId, List<DishOrderRestUpdate> dishUpdates) {
        return dishUpdates.stream().map(u -> toEntity(orderId, u)).toList();
    }

    public DishOrder toEntity(String orderId, DishOrderRestUpdate dishUpdate) {
        DishOrder dishOrder = new DishOrder();

        String dishOrderIdByDAO = dishOrderDAO.getId(orderId, dishUpdate.getDishId());
        String dishOrderId = dishOrderIdByDAO != null ? dishOrderIdByDAO : UUID.randomUUID().toString();
        dishOrder.setId(
                 dishOrderId
        );
        dishOrder.setStatusHistory(
                new OrderStatusHistory(orderStatusRecordDAO.getAllByDishOrderId(dishOrderId))
        );
        dishOrder.setQuantity(dishUpdate.getQuantity());
        dishOrder.setOrderId(orderId);

        Dish dish = new Dish();
        dish.setId(dishUpdate.getDishId());
        dishOrder.setDish(dish);

        return dishOrder;
    }

    public List<DishOrderRest> toDTOs(List<DishOrder> orders) {
        return orders.stream().map(this::toDTO).toList();
    }

    public DishOrderRest toDTO(DishOrder order) {
        DishOrderRest dto = new DishOrderRest();

        dto.setName(order.getDish().getName());
        dto.setQuantityOrdered(order.getQuantity());
        dto.setActualOrderStatus(order.getStatus());
        dto.setUnitPrice(order.getDish().getUnitPrice());

        return dto;
    }
}
