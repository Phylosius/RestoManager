package model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Dish {
    private String id;
    private String name;
    private Double unitPrice;
}
