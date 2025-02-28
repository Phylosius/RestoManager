import model.Ingredient;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientTest {
    @Test
    void check_available_quantities() {

        Double ouefQuantity = Ingredient.getAvalaibleQuantity("3", LocalDateTime.now());
        Double painQuantity = Ingredient.getAvalaibleQuantity("4", LocalDateTime.now());
        Double saucisseQuantity = Ingredient.getAvalaibleQuantity("1", LocalDateTime.now());
        Double huileQuantity = Ingredient.getAvalaibleQuantity("2", LocalDateTime.now());

        assertEquals(80d, ouefQuantity);
        assertEquals(30d, painQuantity);
        assertEquals(10000d, saucisseQuantity);
        assertEquals(20d, huileQuantity);
    }
}
