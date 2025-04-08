package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.dto.DishOrderRest;
import hei.phylosius.restomanager.model.DishOrder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class DishOrderMapper {

    public List<DishOrderRest> toDTOs(List<DishOrder> orders) {
        return orders.stream().map(this::toDTO).toList();
    }

    public DishOrderRest toDTO(DishOrder order) {
        DishOrderRest dto = new DishOrderRest();

        dto.setName(order.getDish().getName());
        dto.setQuantity(order.getQuantity());
        dto.setStatus(order.getStatus());
        dto.setUnitPrice(order.getDish().getUnitPrice());

        return dto;
    }
}
