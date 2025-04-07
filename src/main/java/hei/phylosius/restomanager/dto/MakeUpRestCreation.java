package hei.phylosius.restomanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MakeUpRestCreation {
    private String ingredientId;
    private Double quantity;
}
