import dao.DataSource;
import dao.OrderDAO;
import io.github.cdimascio.dotenv.Dotenv;
import model.DishOrder;
import model.Order;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderDAOTest {

    DataSource dataSource;
    OrderDAO subject;

    @BeforeAll
    void setup() {
        Dotenv dotenv = Dotenv.load();

        dataSource = new DataSource(
                dotenv.get("DB_USERNAME"),
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_URL"));

        subject = new OrderDAO(dataSource);
    }

    @AfterAll
    void tearDown() {
        dataSource.closeConnection();
    }

    @Test
    void get_all_paginated(){

        List<Order> retrievedFirstPageSizeOne = subject.getAll(1, 1);
        List<Order> retrievedSecondPageSizeOne = subject.getAll(2, 1);
        List<Order> retrievedFirstPageSizeFive = subject.getAll(1, 5);

        assertTrue(retrievedFirstPageSizeOne.size() <= 1 && retrievedSecondPageSizeOne.size() <= 1);
        assertTrue(retrievedFirstPageSizeFive.size() <= 5);
    }

    @Test
    void get_one() {

        Order retrieved = subject.getById("-1o");
        List<DishOrder> dishes = retrieved.getDishOrders();
        DishOrder hotDogOrder = dishes.stream().filter(d -> d.getDish().getName().equals("Hot dog")).toList().getFirst();

        assertNotNull(retrieved);
        assertTrue(dishes.size() == 2);
        assertTrue(hotDogOrder.getQuantity() == 4);
    }

}
