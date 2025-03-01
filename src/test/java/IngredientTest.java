import dao.DataSource;
import dao.IngredientDAO;
import io.github.cdimascio.dotenv.Dotenv;
import model.Ingredient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IngredientTest {

    private DataSource dataSource;
    private IngredientDAO ingredientDAO;

    @BeforeAll
    void setUp() {
        Dotenv dotenv = Dotenv.load();
        dataSource = new DataSource(
                dotenv.get("DB_USERNAME"),
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_URL")
        );
        ingredientDAO = new IngredientDAO(dataSource);
    }

    @AfterAll
    void tearDown() {
        dataSource.closeConnection();
    }

    @Test
    void check_available_quantities() {
        Ingredient oeuf = ingredientDAO.getById("3");
        Ingredient pain = ingredientDAO.getById("4");
        Ingredient saucisse = ingredientDAO.getById("1");
        Ingredient huile = ingredientDAO.getById("2");

        Double oeufQuantity = oeuf.getAvailableQuantity();
        Double painQuantity = pain.getAvailableQuantity();
        Double saucisseQuantity = saucisse.getAvailableQuantity();
        Double huileQuantity = huile.getAvailableQuantity();

        assertEquals(80d, oeufQuantity);
        assertEquals(30d, painQuantity);
        assertEquals(10000d, saucisseQuantity);
        assertEquals(20d, huileQuantity);
    }
}
