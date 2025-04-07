package hei.phylosius.restomanager.Repository;


import io.github.cdimascio.dotenv.Dotenv;
import hei.phylosius.restomanager.model.Dish;
import hei.phylosius.restomanager.model.Ingredient;
import hei.phylosius.restomanager.model.MakeUp;
import hei.phylosius.restomanager.model.Criteria;
import hei.phylosius.restomanager.model.LogicalOperator;
import hei.phylosius.restomanager.model.CriteriaOperator;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DishDAOTest {

    private DishDAO subject;
    private Dish testDish;
    private DataSource dataSource;

    @BeforeAll
    public void setUp(){
        Dotenv dotenv = Dotenv.load();

        dataSource = new DataSource(
                dotenv.get("DB_USERNAME"),
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_URL"));

        subject = new DishDAO(dataSource, null);

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

        assertTrue(retrievedFirst.size() <= 5);
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

    @Test
    @Order(7)
    public void check_gross_margin_ok() {

        Dish retrievedDish = subject.getById("1");
        Double grossMargin = retrievedDish.getGrossMargin();

        assertEquals(9500d, grossMargin);
    }

    @Test
    @Order(8)
    public void get_paginated_and_filtered_ok() {
        Criteria nameCriteria = new Criteria(LogicalOperator.AND, "name", CriteriaOperator.NEAR, "hot");
        Criteria priceCriteria1 = new Criteria(LogicalOperator.AND, "unit_price", CriteriaOperator.GREATER_THAN, 1000d);
        Criteria priceCriteria2 = new Criteria(LogicalOperator.AND, "unit_price", CriteriaOperator.LESS_THAN, 20000d);

        List<Dish> retrievedByName = subject.getAllByCriteria(List.of(nameCriteria, priceCriteria1, priceCriteria2), 1, 5);

        assertEquals(1, retrievedByName.size());
    }

    @AfterAll
    public void tearDown(){
        dataSource.closeConnection();
    }
}