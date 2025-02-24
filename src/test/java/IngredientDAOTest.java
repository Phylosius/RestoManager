import dao.DataSource;
import dao.IngredientDAO;
import model.Ingredient;
import model.Price;
import model.Unit;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IngredientDAOTest {

    private IngredientDAO subject;
    private Ingredient testIngredient;
    private Price ingredientPrice;

    @BeforeAll
    public void setUp(){
        Dotenv dotenv = Dotenv.load();

        DataSource dataSource = new DataSource(
                dotenv.get("DB_USERNAME"),
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_URL"));

        subject = new IngredientDAO(dataSource);
        LocalDateTime date = LocalDateTime.now();
        ingredientPrice = new Price(2000.0d, date);
        testIngredient = new Ingredient("0",
                "Test Ingredient", date, ingredientPrice, Unit.L);

    }

    @Test
    @Order(1)
    public void get_ingredient_paginated_ok() {

        List<Ingredient> retrievedFirst = subject.getAll(1, 5);
        List<Ingredient> retrievedSecond = subject.getAll(2, 5);

        assertEquals(4, retrievedFirst.size());
        assertEquals(0, retrievedSecond.size());
    }

    @Test
    @Order(2)
    public void create_ingredient_ok() {
        List<Ingredient> retrievedFirst = subject.getAll(1, 20);

        subject.save(testIngredient);

        List<Ingredient> retrievedSecond = subject.getAll(1, 20);
        assertEquals(retrievedFirst.size() + 1, retrievedSecond.size());
    }

    @Test
    @Order(3)
    public void update_ingredient_ok() {
        ingredientPrice.setValue(10d);
        ingredientPrice.setDate(LocalDateTime.now());
        testIngredient.setPrice(ingredientPrice);

        subject.update(testIngredient.getId(), testIngredient);

        Ingredient retrievedIngredient = subject.getById(testIngredient.getId());

        assertEquals(testIngredient.getPrice().getValue(), retrievedIngredient.getPrice().getValue());
    }

    @Test
    @Order(4)
    public void get_ingredient_ok() {

        Ingredient retrievedIngredient = subject.getById(testIngredient.getId());

        assertEquals(retrievedIngredient.getPrice().getValue(), ingredientPrice.getValue());
    }

    @Test
    @Order(5)
    public void delete_ingredient_ok() {

        subject.delete(testIngredient.getId());

        Ingredient retrievedIngredient = subject.getById(testIngredient.getId());

        assertNull(retrievedIngredient.getId());
    }
}
