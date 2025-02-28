import dao.DataSource;
import dao.StockMovementDAO;
import io.github.cdimascio.dotenv.Dotenv;
import model.*;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StockMovementDAOTest {
    private StockMovementDAO subject;
    private DataSource dataSource;

    @BeforeAll
    void setUp() {
        Dotenv dotenv = Dotenv.load();

        dataSource = new DataSource(
                dotenv.get("DB_USERNAME"),
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_URL"));
        subject = new StockMovementDAO(dataSource);
    }

    @Test
    void get_filtered_ordered_and_paginated() {
        Criteria quantityCriteria = new Criteria(LogicalOperator.AND, "quantity", CriteriaOperator.GREATER_THAN, 30d);
        Criteria typeCriteria = new Criteria(LogicalOperator.AND, "type", CriteriaOperator.EQUAL, MovementType.IN);
        int pageSize = 10;

        List<StockMovement> retrieved = subject.getAllByCriteria(List.of(quantityCriteria, typeCriteria), 1, pageSize);

        assertTrue(retrieved.size() <= pageSize);
        assertEquals(3, retrieved.size());
    }

    @AfterAll
    public void tearDown(){
        dataSource.closeConnection();
    }
}
