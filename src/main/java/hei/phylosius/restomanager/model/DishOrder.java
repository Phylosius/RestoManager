package model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DishOrder {
    private String id;
    private String orderId;
    private Dish dish;
    private int quantity;
    private OrderStatusHistory statusHistory;

    public DishOrder(Dish dish, int quantity){
        this.id = UUID.randomUUID().toString();
        this.dish = dish;
        this.quantity = quantity;
        this.statusHistory = new OrderStatusHistory();
    }

    public DishOrder(String id, String orderId, Dish dish, int quantity){
        this.id = id;
        this.orderId = orderId;
        this.dish = dish;
        this.quantity = quantity;
        this.statusHistory = new OrderStatusHistory();
    }

    public List<MakeUp> getMissingIngredients(LocalDateTime date) {
        List<MakeUp> missingMakeUps = new ArrayList<>();

        List<MakeUp> additionalMissingMakeUps = dish.getMissingIngredients(date);

        if (!additionalMissingMakeUps.isEmpty()){
            missingMakeUps.addAll(additionalMissingMakeUps);

            if (quantity > 1) {
                List<Ingredient> missingIngredients = missingMakeUps.stream().map(MakeUp::getIngredient).toList();

                List<MakeUp> makingMakeUps = dish.getMakeUps();
                Double quantityMultiplier = quantity - 1d;

                makingMakeUps.forEach(making -> {
                    if (missingIngredients.contains(making.getIngredient())) {
                        for (MakeUp missing:  missingMakeUps) {
                            if (missing.getIngredient().equals(making.getIngredient())) {
                                missing.setQuantity(
                                        missing.getQuantity() + (making.getQuantity() * quantityMultiplier)
                                );
                            }
                        }
                    } else {
                        making.setQuantity(making.getQuantity() * quantityMultiplier);

                        missingMakeUps.add(making);
                    }
                });
            }
        }

        return missingMakeUps;
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
