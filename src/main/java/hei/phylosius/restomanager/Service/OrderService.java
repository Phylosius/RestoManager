package hei.phylosius.restomanager.Service;

import hei.phylosius.restomanager.Repository.DishOrderDAO;
import hei.phylosius.restomanager.Repository.OrderDAO;
import hei.phylosius.restomanager.dto.DishOrderRestUpdate;
import hei.phylosius.restomanager.dto.OrderRest;
import hei.phylosius.restomanager.mappers.DishOrderMapper;
import hei.phylosius.restomanager.mappers.OrderMapper;
import hei.phylosius.restomanager.model.DishOrder;
import hei.phylosius.restomanager.model.Order;
import hei.phylosius.restomanager.model.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class OrderService {

    private final DishOrderDAO dishOrderDAO;
    private DishOrderMapper dishOrderMapper;
    private OrderDAO orderDAO;
    private OrderMapper orderMapper;

    public OrderRest getOrderInfo(String orderId) {
        Order order = orderDAO.getById(orderId);

        return orderMapper.toDTO(order);
    }

    public List<DishOrderRestUpdate> updateDishes(String orderId, List<DishOrderRestUpdate> dishes) {
        List<DishOrder> dishOrders = dishOrderMapper.toEntities(orderId, dishes);

        dishOrderDAO.saveAll(dishOrders);

        return dishes;
    }

    public Boolean updateDishStatus(String orderId, String dishId, OrderStatus status) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
