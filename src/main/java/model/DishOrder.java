package model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DishOrder {
    private String id;
    private Dish dish;
    private int quantity;
    private OrderStatusHistory statusHistory;

    public DishOrder(Dish dish, int quantity){
        this.id = UUID.randomUUID().toString();
        this.dish = dish;
        this.quantity = quantity;
        this.statusHistory = new OrderStatusHistory();
    }

    public void addOrderStatusRecord(OrderStatusRecord record){
            statusHistory.addOrderStatusRecord(record);
    }

    public OrderStatus getStatus(){
        return statusHistory.getLatestRecord().getStatus();
    }

    public OrderStatusRecord getLatestRecord(){
        return statusHistory.getLatestRecord();
    }

    public LocalDateTime getCreationDate(){
        return statusHistory.getCreationRecord().getDate();
    }

    public Double getTotalPrice(){
        return dish.getUnitPrice() *  quantity;
    }
}
