package hei.phylosius.restomanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRestGet {
    private String id;
    private String reference;
    private LocalDateTime creationDate;
}
