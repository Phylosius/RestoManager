import dao.DataSource;
import dao.IngredientDAO;
import model.Ingredient;
import model.Unit;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IngredientDAOTest {

    private DataSource dataSource;
    private IngredientDAO subject;
    private Ingredient testIngredient;

    @BeforeAll
    public void setUp(){
        Dotenv dotenv = Dotenv.load();

        dataSource = new DataSource(
                dotenv.get("DB_USERNAME"),
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_URL"));
        subject = new IngredientDAO(dataSource);
        testIngredient = new Ingredient("0",
                "Test Ingredient", LocalDate.now(), 2000.0d, Unit.L);

    }

    @Test
    @Order(1)
    public void get_ingredient_paginated_ok() {

        List<Ingredient> retrievedFirst = subject.getAll(1, 5);
        List<Ingredient> retrievedSecond = subject.getAll(2, 5);
        List<Ingredient> retrievedThird = subject.getAll(3, 5);

        assertEquals(5, retrievedFirst.size());
        assertEquals(3, retrievedSecond.size());
        assertEquals(0, retrievedThird.size());
    }

    @Test
    @Order(2)
    public void create_ingredient_ok() {

        subject.save(testIngredient);

        Ingredient retrievedIngredient = subject.getById(testIngredient.getId());
        assertEquals(testIngredient, retrievedIngredient);
    }

    @Test
    @Order(3)
    public void update_ingredient_ok() {
        testIngredient.setUnitPrice(0.0d);

        subject.update(testIngredient.getId(), testIngredient);
        Ingredient retrievedIngredient = subject.getById(testIngredient.getId());

        assertEquals(testIngredient.getUnitPrice(), retrievedIngredient.getUnitPrice());
    }

    @Test
    @Order(4)
    public void get_ingredient_ok() {

        Ingredient retrievedIngredient = subject.getById(testIngredient.getId());

        assertEquals(testIngredient, retrievedIngredient);
    }

    @Test
    @Order(5)
    public void delete_ingredient_ok() {

        subject.delete(testIngredient.getId());

        Ingredient retrievedIngredient = subject.getById(testIngredient.getId());

        assertNull(retrievedIngredient.getId());
    }
}
