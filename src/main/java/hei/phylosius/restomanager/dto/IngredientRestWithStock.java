package hei.phylosius.restomanager.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class IngredientRestWithStock extends IngredientRest {
    private Double stock;

    public IngredientRestWithStock(IngredientRest dto, Double stock) {
        this(dto.getId(), dto.getName(), dto.getUnitPrice(), dto.getUpdatedAt(), stock);
    }

    public IngredientRestWithStock(String id, String name, Double unitPrice, LocalDateTime modificationDate, Double stock) {
        super(id, name, unitPrice, modificationDate);
        this.stock = stock;
    }

    public IngredientRestWithStock() {
        super();
    }

}
