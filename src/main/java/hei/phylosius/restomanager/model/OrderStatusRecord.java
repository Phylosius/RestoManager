package hei.phylosius.restomanager.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusRecord {
    private String dishOrderId;
    private LocalDateTime date;
    private OrderStatus status;

    public OrderStatusRecord(LocalDateTime date, OrderStatus status){
        this.date = date;
        this.status = status;
    }
}
