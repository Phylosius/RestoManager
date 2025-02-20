package model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    private String id;
    private String name;
    private LocalDate modificationDate;
    private Double unitPrice;
    private Unit unit;
}
