package hei.phylosius.restomanager.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class IngredientRestWithStock extends IngredientRest {
    private int stock;

    public IngredientRestWithStock(String id, String name, Double unitPrice, LocalDateTime modificationDate, int stock) {
        super(id, name, unitPrice, modificationDate);
        this.stock = stock;
    }

    public IngredientRestWithStock() {
        super();
    }

}
