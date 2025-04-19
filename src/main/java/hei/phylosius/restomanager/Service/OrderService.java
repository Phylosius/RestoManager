package hei.phylosius.restomanager.Service;

import hei.phylosius.restomanager.Repository.DishOrderDAO;
import hei.phylosius.restomanager.Repository.OrderDAO;
import hei.phylosius.restomanager.Repository.OrderStatusRecordDAO;
import hei.phylosius.restomanager.dto.OrderRest;
import hei.phylosius.restomanager.dto.UpdateOrderRest;
import hei.phylosius.restomanager.mappers.DishOrderMapper;
import hei.phylosius.restomanager.mappers.OrderMapper;
import hei.phylosius.restomanager.model.Order;
import hei.phylosius.restomanager.model.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Component
public class OrderService {

    private final DishOrderDAO dishOrderDAO;
    private final OrderStatusRecordDAO orderStatusRecordDAO;
    private DishOrderMapper dishOrderMapper;
    private OrderDAO orderDAO;
    private OrderMapper orderMapper;

    public OrderRest getOrderInfoByReference(String reference) {
        Order order = orderDAO.getByReference(reference);

        return orderMapper.toDTO(order);
    }

    public void createEmptyOrder(String reference) {
        Order order = new Order();
        order.setReference(reference);
        order.setDishOrders(List.of());
        order.setId(String.valueOf((int) Instant.now().toEpochMilli()));
        order.setCreationDate(LocalDateTime.now());

        orderDAO.save(order);
    }

    public Boolean isReferenceInUse(String reference) {
        Order order = orderDAO.getByReference(reference);
        return order.getId() != null;
    }

    public Boolean updateDishStatus(String orderId, String dishId, OrderStatus status) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void updateDishes(String reference, List<UpdateOrderRest> dishes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
