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

    public void setProcessingTime(Long processingTime, String format) {
        if (format.contains("H")) {
            this.processingTime = String.format("%sh", processingTime / 3600) ;
        } else if (format.contains("M")) {
            this.processingTime = String.format("%sm", processingTime / 60) ;
        } else if (format.contains("S")) {
            this.processingTime = String.format("%ss", processingTime) ;
        } else {
            throw new IllegalArgumentException("Invalid format: " + format);
        }


    }
}
