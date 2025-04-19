package hei.phylosius.restomanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateDishOrderRest {
    private Integer dishIdentifier;
    private Integer quantityOrdered;
}
