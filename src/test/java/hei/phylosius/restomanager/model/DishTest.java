package hei.phylosius.restomanager.model;

import hei.phylosius.restomanager.Repository.DataSource;
import hei.phylosius.restomanager.Repository.DishDAO;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DishTest {
    private DataSource dataSource;
    private DishDAO dishDAO;


    @BeforeAll
    void setUp() {
        Dotenv dotenv = Dotenv.load();
        dataSource = new DataSource(
                dotenv.get("DB_USERNAME"),
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_URL")
        );
        dishDAO = new DishDAO(dataSource, null);
    }

    @AfterAll
    void tearDown() {
        dataSource.closeConnection();
    }

    @Test
    void check_available_quantity() {
        Dish subject = dishDAO.getById("1");
        int expectedQuantity = 30;

        int actualQuantity = subject.getAvailableQuantity();

        assertEquals(expectedQuantity, actualQuantity);
    }
}
