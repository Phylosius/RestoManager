package hei.phylosius.restomanager.Service;

import hei.phylosius.restomanager.dto.DishOrderRestUpdate;
import hei.phylosius.restomanager.dto.OrderRest;
import hei.phylosius.restomanager.model.OrderStatus;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class OrderService {

    public OrderRest getOrderInfo(String orderId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<DishOrderRestUpdate> updateDishes(String orderId, List<DishOrderRestUpdate> dishes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Boolean updateDishStatus(String orderId, String dishId, OrderStatus status) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
