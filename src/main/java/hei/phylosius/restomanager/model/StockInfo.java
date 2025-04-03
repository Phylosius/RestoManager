package hei.phylosius.restomanager.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class StockInfo {
    private Double totalOutQuantity;
    private Double totalInQuantity;

    public Double getQuantity() {
        return totalInQuantity - totalOutQuantity;
    }
}
