import dao.DataSource;
import dao.IngredientDAO;
import dao.StockMovementDAO;
import io.github.cdimascio.dotenv.Dotenv;
import model.*;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StockMovementDAOTest {
    private StockMovementDAO subject;
    private DataSource dataSource;

    private final LocalDateTime now = LocalDateTime.of(2025, 2, 25, 5, 5);

    private Ingredient ingredient1;
    private Ingredient ingredient2;

    @BeforeAll
    void setUp() {
        Dotenv dotenv = Dotenv.load();

        dataSource = new DataSource(
                dotenv.get("DB_USERNAME"),
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_URL"));
        subject = new StockMovementDAO(dataSource);

        // Test data
        ingredient1 = new Ingredient("-1", "Sel",
                now,new Price(2.5, now), Unit.G);
        ingredient2 = new Ingredient("-2", "Riz",
                now,new Price(3.5, now), Unit.G);
        IngredientDAO.save(dataSource.getConnection(), ingredient1);
        IngredientDAO.save(dataSource.getConnection(), ingredient2);
    }

    @Test
    @Order(1)
    void get_filtered_ordered_and_paginated() {
        Criteria quantityCriteria = new Criteria(LogicalOperator.AND, "quantity", CriteriaOperator.GREATER_THAN, 30d);
        Criteria typeCriteria = new Criteria(LogicalOperator.AND, "type", CriteriaOperator.EQUAL, MovementType.IN);
        int pageSize = 10;

        List<StockMovement> retrieved = subject.getAllByCriteria(List.of(quantityCriteria, typeCriteria), 1, pageSize);

        assertTrue(retrieved.size() <= pageSize);
        assertEquals(3, retrieved.size());
    }

    @Test
    @Order(2)
    void create_in_movements_ok() {
        StockMovement selMovement = new StockMovement(ingredient1, MovementType.IN, 10000d, now);
        StockMovement rizMovement = new StockMovement(ingredient2, MovementType.IN, 20000d, now);

        subject.saveAll(List.of(selMovement, rizMovement));
        Criteria dateCriteria = new Criteria(LogicalOperator.AND, "date", CriteriaOperator.NEAR, now, 100d);
        List<StockMovement> retrieved = subject.getAllByCriteria(List.of(dateCriteria), 1, 5);

        assertTrue(!retrieved.isEmpty() && retrieved.size() <= 5);
        assertTrue(retrieved.contains(selMovement));
        assertTrue(retrieved.contains(rizMovement));
    }

    @Test
    @Order(3)
    void create_out_movements_ok() {
        LocalDateTime then = LocalDateTime.of(2025, 2, 25, 15, 5);
        StockMovement selMovement = new StockMovement(ingredient1, MovementType.OUT, 5000d, then);
        StockMovement rizMovement = new StockMovement(ingredient2, MovementType.OUT, 15000d, then);

        subject.saveAll(List.of(selMovement, rizMovement));
        Criteria criteria = new Criteria(LogicalOperator.AND, "type", CriteriaOperator.EQUAL, MovementType.OUT);
        Criteria dateCriteria = new Criteria(LogicalOperator.AND, "date", CriteriaOperator.NEAR, then, 2000d);
        List<StockMovement> retrieved = subject.getAllByCriteria(List.of(criteria, dateCriteria), 1, 5);

        assertEquals(2, retrieved.size());
        assertTrue(retrieved.contains(selMovement));
        assertTrue(retrieved.contains(rizMovement));
    }

    @Test
    @Order(4)
    void check_total_quantity_ok() {

        Double selQuantity = Ingredient.getAvailableQuantity("-1", LocalDateTime.now());
        Double rizQuantity = Ingredient.getAvailableQuantity("-2", LocalDateTime.now());

        assertEquals(5000d, selQuantity);
        assertEquals(5000d, rizQuantity);
    }

    @AfterAll
    public void tearDown(){
        IngredientDAO.delete(dataSource.getConnection(), ingredient1.getId());
        IngredientDAO.delete(dataSource.getConnection(), ingredient2.getId());

        dataSource.closeConnection();
    }
}
