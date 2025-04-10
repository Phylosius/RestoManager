package hei.phylosius.restomanager.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DishProcessingTimeRest {
    private String processingTime;
    private ProcessingTimeType processingTimeType;
}
