package hei.phylosius.restomanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class IngredientDTO {
    private String id;
    private String name;
    private Double unitPrice;
    private LocalDateTime updatedAt;
}
