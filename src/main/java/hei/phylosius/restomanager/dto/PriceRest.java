package hei.phylosius.restomanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceRest {
    private Integer id;
    private Double price;
    private String dateValue;
}
