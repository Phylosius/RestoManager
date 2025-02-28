import model.Ingredient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IngredientTest {
    private Ingredient subject;

    @Test
    void check_available_quantities() {

        Double ouefQuantity = subject.getAvalaibleQuantity("3", LocalDateTime.now());
        Double painQuantity = subject.getAvalaibleQuantity("4", LocalDateTime.now());
        Double saucisseQuantity = subject.getAvalaibleQuantity("1", LocalDateTime.now());
        Double huileQuantity = subject.getAvalaibleQuantity("2", LocalDateTime.now());

        assertEquals(80d, ouefQuantity);
        assertEquals(30d, painQuantity);
        assertEquals(10000d, saucisseQuantity);
        assertEquals(20d, huileQuantity);
    }
}
