import dao.DataSource;
import dao.DishDAO;
import dao.IngredientDAO;
import model.Dish;
import model.Ingredient;
import model.MakeUp;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DishDAOTest {

    private DataSource dataSource;
    private DishDAO subject;
    private Dish testDish;

    @BeforeAll
    public void setUp(){
        Dotenv dotenv = Dotenv.load();

        dataSource = new DataSource(
                dotenv.get("DB_USERNAME"),
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_URL"));
        subject = new DishDAO(dataSource);

        List<Ingredient> testIngredients = IngredientDAO.getAll(dataSource.getConnection(), 1, 3);
        List<MakeUp> makeUps = List.of(
                new MakeUp(testIngredients.get(0), 2d),
                new MakeUp(testIngredients.get(1), 3d),
                new MakeUp(testIngredients.get(2), 1d));

        testDish = new Dish("0",
                "Test Dish", 2000.0d, makeUps);

    }

    @Test
    @Order(1)
    public void get_dish_paginated_ok() {

        List<Dish> retrievedFirst = subject.getAll(1, 5);

        assertEquals(1, retrievedFirst.size());
    }

    @Test
    @Order(2)
    public void create_dish_ok() {

        subject.save(testDish);

        Dish retrievedDish = subject.getById(testDish.getId());
        assertEquals(testDish, retrievedDish);
    }

    @Test
    @Order(3)
    public void update_dish_ok() {
        testDish.setUnitPrice(0.0d);

        subject.update(testDish.getId(), testDish);
        Dish retrievedDish = subject.getById(testDish.getId());

        assertEquals(testDish.getUnitPrice(), retrievedDish.getUnitPrice());
    }

    @Test
    @Order(4)
    public void get_dish_ok() {

        Dish retrievedDish = subject.getById(testDish.getId());

        assertEquals(testDish, retrievedDish);
    }

    @Test
    @Order(5)
    public void delete_dish_ok() {

        subject.delete(testDish.getId());
        Dish retrievedDish = subject.getById(testDish.getId());

        assertNull(retrievedDish.getId());
    }

    @Test
    @Order(6)
    public void check_total_recipe_price_ok() {

        Dish retrievedDish = subject.getById("1");
        Double totalIngredientCost = retrievedDish.getProductionCost();

        assertEquals(5500d, totalIngredientCost);
    }
}