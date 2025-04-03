package hei.phylosius.restomanager.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Price {
    private Double value;
    private LocalDateTime date;
}
