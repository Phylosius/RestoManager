package hei.phylosius.restomanager.model;

import hei.phylosius.restomanager.dao.DataSource;
import hei.phylosius.restomanager.dao.DishDAO;
import hei.phylosius.restomanager.dao.DishOrderDAO;
import hei.phylosius.restomanager.dao.OrderDAO;
import io.github.cdimascio.dotenv.Dotenv;
import hei.phylosius.restomanager.model.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderTest {

    Order subject;
    DataSource dataSource;
    OrderDAO orderDAO;
    DishDAO dishDAO;
    DishOrderDAO dishOrderDAO;

    @BeforeAll
    void setUp() {
        Dotenv dotenv = Dotenv.load();

        dataSource = new DataSource(
                dotenv.get("DB_USERNAME"),
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_URL"));

        orderDAO = new OrderDAO(dataSource);
        dishOrderDAO = new DishOrderDAO(dataSource);
        dishDAO =  new DishDAO(dataSource);

        subject = new Order("-test", LocalDateTime.now());
    }

    @AfterAll
    void tearDown() {
        dataSource.closeConnection();
    }

    @Test
    void add_unmakeable_dishes() {
        Dish omelette = dishDAO.getById("-1");
        OrderStatusHistory omeletterOrderStatusHistory = new OrderStatusHistory();
        DishOrder tooManyOmeletteOrder = new DishOrder("-test-omeletteOrder", subject.getId(), omelette, 200, omeletterOrderStatusHistory);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> subject.addDishOrder(tooManyOmeletteOrder));

        assertEquals("Dish order have missing ingredients: \nMissing Ingredients for Omelette\n\nHuile : 10.000000000000002 L\nOeuf : 200.0 U", exception.getMessage());
    }

    @Test
    void add_dish() {
        int pastOrdersSize = subject.getDishOrders().size();
        Dish omelette = dishDAO.getById("-1");
        OrderStatusHistory omeletterOrderStatusHistory = new OrderStatusHistory();
        DishOrder omeletteOrder = new DishOrder("-test-omeletteOrder", subject.getId(), omelette, 20000, omeletterOrderStatusHistory);

        subject.addDishOrder(omeletteOrder);
        int actualSize = subject.getDishOrders().size();

        assertTrue(pastOrdersSize < actualSize);
        assertTrue(subject.getDishOrders().contains(omeletteOrder));
    }

}
