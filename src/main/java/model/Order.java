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
            List<OrderStatus> dishesStatuses = getDishOrders().stream().map(DishOrder::getStatus).toList();

            Map<OrderStatus, Integer> frequencyMap = new HashMap<>();

            for (OrderStatus dishStatus : dishesStatuses){
                frequencyMap.put(dishStatus, frequencyMap.getOrDefault(dishStatus, 0) + 1);
            }

            int maxFrequency = -1;

            for (Map.Entry<OrderStatus, Integer> entry : frequencyMap.entrySet()) {
                if (entry.getValue() > maxFrequency){
                    status = entry.getKey();
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
