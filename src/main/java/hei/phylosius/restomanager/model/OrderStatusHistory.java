package hei.phylosius.restomanager.model;

import lombok.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class OrderStatusHistory {
    private List<OrderStatusRecord> records;

    public  OrderStatusHistory(){
        this.records = new ArrayList<>();
    }

    public OrderStatus expectedRecordStatus(){
        if (records.isEmpty()) {
            return OrderStatus.CREATED;
        } else {
            OrderStatusRecord latestRecord = getLatestRecord();
            OrderStatus latestStatus = latestRecord.getStatus();

            return switch (latestStatus) {
                case CREATED -> OrderStatus.CONFIRMED;
                case CONFIRMED -> OrderStatus.IN_PROGRESS;
                case IN_PROGRESS -> OrderStatus.FINISHED;
                case FINISHED -> OrderStatus.DELIVERED;
                case DELIVERED -> null;
            };
        }
    }

    public void addOrderStatusRecord(OrderStatusRecord record){
        if (expectedRecordStatus() == record.getStatus()){
            records.add(record);
        } else {
            throw new IllegalArgumentException("The record status should be " +  expectedRecordStatus());
        }
    }

    public OrderStatusRecord getCreationRecord(){
        return records.stream().filter(a -> a.getStatus().equals(OrderStatus.CREATED))
                .findFirst()
                .orElse(null);
    }

    public OrderStatusRecord getLatestRecord() {
        return records.stream().max(Comparator.comparing(OrderStatusRecord::getDate))
                .orElse(null);
    }
}
