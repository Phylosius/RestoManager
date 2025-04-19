package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.Repository.DishDAO;
import hei.phylosius.restomanager.Repository.DishOrderDAO;
import hei.phylosius.restomanager.Repository.OrderStatusRecordDAO;
import hei.phylosius.restomanager.dto.CreateDishOrderRest;
import hei.phylosius.restomanager.dto.DishOrderRest;
import hei.phylosius.restomanager.dto.DishOrderRestUpdate;
import hei.phylosius.restomanager.model.Dish;
import hei.phylosius.restomanager.model.DishOrder;
import hei.phylosius.restomanager.model.OrderStatusHistory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Component
public class DishOrderMapper {

    private final DishDAO dishDAO;
    private DishOrderDAO dishOrderDAO;
    private OrderStatusRecordDAO orderStatusRecordDAO;

    public List<DishOrder> toEntities(String orderId, List<DishOrderRest> DTOs) {
        return DTOs.stream().map(u -> toEntity(orderId, u)).toList();
    }

    public List<DishOrder> toEntitiesByCreate(String orderId, List<CreateDishOrderRest> DTOs) {
        return DTOs.stream().map(u -> toEntity(orderId, u)).toList();
    }

    public DishOrder toEntity(String orderId, CreateDishOrderRest createDTO) {
        DishOrder dishOrder = new DishOrder();

        String dishOrderId = String.format("%s.%s", orderId.hashCode(), createDTO.getDishIdentifier());

        dishOrder.setOrderId(orderId);
        dishOrder.setQuantity(createDTO.getQuantityOrdered());
        dishOrder.setId(dishOrderId);
        dishOrder.setDish(
                dishDAO.getById(createDTO.getDishIdentifier().toString())
        );
        dishOrder.setStatusHistory(
                new OrderStatusHistory(
                        orderStatusRecordDAO.getAllByDishOrderId(dishOrderId)
                )
        );

        return dishOrder;
    }

    public DishOrder toEntity(String orderId, DishOrderRest dto) {
        DishOrder dishOrder = new DishOrder();

        dishOrder.setId(String.valueOf((int) (Instant.now().toEpochMilli())));
        dishOrder.setOrderId(orderId);
        dishOrder.setQuantity(dto.getQuantityOrdered());
        dishOrder.setStatusHistory(
                new OrderStatusHistory(orderStatusRecordDAO.getAllByDishOrderId(orderId))
        );

        return dishOrder;
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
