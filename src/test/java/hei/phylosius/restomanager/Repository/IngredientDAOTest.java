package hei.phylosius.restomanager.Repository;

import io.github.cdimascio.dotenv.Dotenv;
import hei.phylosius.restomanager.model.Price;
import hei.phylosius.restomanager.model.Ingredient;
import hei.phylosius.restomanager.model.Criteria;
import hei.phylosius.restomanager.model.Unit;
import hei.phylosius.restomanager.model.LogicalOperator;
import hei.phylosius.restomanager.model.CriteriaOperator;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IngredientDAOTest {

    private IngredientDAO subject;
    private Ingredient testIngredient;
    private Price ingredientPrice;
    private DataSource dataSource;

    @BeforeAll
    public void setUp(){
        Dotenv dotenv = Dotenv.load();

        dataSource = new DataSource(
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

        assertNull(retrievedIngredient);
    }

    @Test
    @Order(6)
    public void get_paginated_and_filtered_ok() {
        Criteria unitCriteria = new Criteria(LogicalOperator.AND, "unit", CriteriaOperator.EQUAL, Unit.U);
        int pageSize = 5;

        List<Ingredient> retrievedByUnit = subject.getAllByCriteria(List.of(unitCriteria), 1, pageSize);

        assertTrue(retrievedByUnit.size() <= pageSize);
        assertEquals(2, retrievedByUnit.size());
    }

    @AfterAll
    public void tearDown(){
        dataSource.closeConnection();
    }
}
