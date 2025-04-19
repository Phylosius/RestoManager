package hei.phylosius.restomanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class PriceRest {
    private Integer id;
    private Double price;
    private String dateValue;
}
