package hei.phylosius.restomanager.dto;

import hei.phylosius.restomanager.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObjectWithStatus {
    private OrderStatus status;
}
