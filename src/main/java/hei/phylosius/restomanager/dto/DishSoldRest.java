package hei.phylosius.restomanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishSoldRest {
    private Integer dishIdentifier;
    private String dishName;
    private Integer quantitySold;
}
