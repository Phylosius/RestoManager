package model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String id;
    private List<DishOrder> dishOrders;
    private LocalDateTime creationDate;

    public Order(LocalDateTime creationDate) {
        this.id = UUID.randomUUID().toString();
        this.creationDate = creationDate;
        this.dishOrders = new ArrayList<>();
    }

    public void confirm(){
        LocalDateTime confirmationDate = LocalDateTime.now();

        dishOrders.forEach(dishOrder -> {
            OrderStatusRecord confirmationRecord = new OrderStatusRecord
                    (dishOrder.getDish().getId(),
                            confirmationDate,
                            OrderStatus.CONFIRMED);

            dishOrder.addOrderStatusRecord(confirmationRecord);
        });
    }

    public void addDishOrder(DishOrder dishOrder){
        dishOrders.add(dishOrder);
    }

    public LocalDateTime getStatusDate(OrderStatus status) {
        if (!status.isAfter(getActualStatus())) {
            List<DishOrder> dishesWithStatus = dishOrders.stream().filter(dishOrder -> dishOrder.getStatus().equals(status)).toList();
            LocalDateTime statusDate = dishesWithStatus.getFirst()
                    .getLatestRecord()
                    .getDate();
            for (int i = 1; i < dishesWithStatus.size(); i++) {
                LocalDateTime actualDate = dishesWithStatus.get(i).getLatestRecord().getDate();
                if  (actualDate.isBefore(statusDate)) {
                    statusDate = actualDate;
                }
            }
            return statusDate;
        }  else {
            throw new IllegalArgumentException("The order don't have the given status");
        }
    }

    public OrderStatus getActualStatus(){
        OrderStatus status = OrderStatus.CREATED;

        if (!dishOrders.isEmpty()) {
            for (DishOrder dishOrder : dishOrders) {
                OrderStatus dishStatus = dishOrder.getStatus();
                if (!dishStatus.isAfter(status)) {
                    status = dishStatus;
                }
            }
        }

        return status;
    }

    public Double getTotalAmount() {
        return dishOrders.stream()
                .map(DishOrder::getTotalPrice)
                .reduce(0d, Double::sum);
    }
}
