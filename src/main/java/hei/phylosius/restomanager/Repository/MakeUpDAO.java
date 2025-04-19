package hei.phylosius.restomanager.Repository;

import hei.phylosius.restomanager.model.Criteria;
import hei.phylosius.restomanager.model.MakeUp;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class MakeUpDAO{

    private final DataSource dataSource;

    public MakeUpDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public List<MakeUp> getAllByDishID(String dishID){
        return getAllByDishID(dataSource.getConnection(), dishID);
    }

    public List<MakeUp> getAllByCriteria(List<Criteria> criteria, int page, int pageSize) {
        return getAllByCriteria(dataSource.getConnection(), criteria, page, pageSize);
    }

    public void saveAll(String dishID, List<MakeUp> makeUpList) {
        makeUpList.forEach(m -> save(dishID, m));
    }

    public void save(String dishID, MakeUp makeUp) {
        if (isExist(dataSource.getConnection(), dishID, makeUp.getIngredient().getName())) {
            update(dataSource.getConnection(), dishID, makeUp.getIngredient().getId(), makeUp.getQuantity());
        } else {
            add(dataSource.getConnection(), dishID, makeUp);
        }
    }

    public int addAll(String dishID, List<MakeUp> makeUpList){
        return addAll(dataSource.getConnection(),dishID, makeUpList);
    }

    public int deleteByDishID(String dishID){
        return deleteByDishID(dataSource.getConnection(), dishID);
    }

    public static List<MakeUp> getAllByDishID(Connection conn, String dishID){
        List<MakeUp> makeUps = new ArrayList<>();

        String sql = "SELECT ingredient_id, ingredient_quantity FROM make_up WHERE dish_id = ?";
        List<Object> params = List.of(dishID);

        BaseDAO.executeQuery(conn, sql, params, (res) -> {
            while(res.next()){
                MakeUp makeUp = new MakeUp();

                makeUp.setIngredient(
                        IngredientDAO.getById(conn, res.getString("ingredient_id")));
                makeUp.setQuantity(
                        res.getDouble("ingredient_quantity"));

                makeUps.add(makeUp);
            }
        });

        return makeUps;
    }

    public static List<MakeUp> getAllByCriteria(Connection conn, List<Criteria> criteria, int page, int pageSize){
        List<MakeUp> makeUps = new ArrayList<>();

        String sql = "SELECT ingredient_id, ingredient_quantity FROM make_up WHERE 1=1";

        BaseDAO.getAllByCriteria(conn, criteria, page, pageSize, sql, res -> {
            while(res.next()){
                MakeUp makeUp = new MakeUp();

                makeUp.setIngredient(
                        IngredientDAO.getById(conn, res.getString("ingredient_id"))
                );
                makeUp.setQuantity(res.getDouble("ingredient_quantity"));

                makeUps.add(makeUp);
            }
        });

        return makeUps;
    }

    public static int update(Connection conn, String dishId, String ingredientId, Double ingredientQuantity){

        String sql = "UPDATE make_up SET ingredient_quantity = ? WHERE ingredient_id = ? AND dish_id = ?";
        List<Object> params = List.of(ingredientQuantity, ingredientId, dishId);

        return BaseDAO.executeUpdate(conn, sql, params);
    }

    public static void add(Connection conn, String dishId, MakeUp makeUp){
        String sql = "INSERT INTO make_up (dish_id, ingredient_id, ingredient_quantity) VALUES (?, ?, ?)";
        List<Object> params = List.of(dishId, makeUp.getIngredient().getId(), makeUp.getQuantity());

        BaseDAO.executeUpdate(conn, sql, params);
    }

    public static int addAll(Connection conn, String dishID, List<MakeUp> makeUps){
        int saved;

        if (!DishDAO.isExist(conn, dishID)) {
            throw new DishNotFoundException(String.format("Dish of id %s not found", dishID));
        }

        makeUps.forEach(makeUp -> {
            if (!IngredientDAO.isExist(conn, makeUp.getIngredient().getId())) {
                throw new IngredientNotFoundException(String.format("Ingredient of id %s not found", makeUp.getIngredient().getId()));
            }
        });

        String sql;
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO make_up (dish_id, ingredient_id, ingredient_quantity) VALUES ");
        List<Object> params = new ArrayList<>();

        for (int i = 0; i < makeUps.size(); i++) {
            MakeUp makeUp = makeUps.get(i);
            String formater = i == makeUps.size() - 1 ? "(%s, %s, %s)" : "(%s, %s, %s),";
            sqlBuilder.append(String.format(formater, dishID, makeUp.getIngredient().getId(), makeUp.getQuantity()));
        }

        sql = sqlBuilder.toString();

        saved = BaseDAO.executeUpdate(conn, sql, params);

        return saved;
    }

    public static int deleteByDishID(Connection conn, String dishID){
        int deleted;

        String sql = "DELETE FROM make_up WHERE dish_id = ?";
        List<Object> params = List.of(dishID);

        deleted = BaseDAO.executeUpdate(conn, sql, params);

        return deleted;
    }

    public static Boolean isExist(Connection conn, String dishID, String ingredientName){
        String sql = """
                SELECT m.ingredient_quantity
                FROM make_up m
                JOIN ingredient i
                    ON m.ingredient_id = i.id
                WHERE m.dish_id = ?
                    AND i.name = ?
                """;
        List<Object> params = List.of(dishID, ingredientName);

        AtomicReference<Boolean> isExist = new AtomicReference<>(false);
        BaseDAO.executeQuery(conn, sql, params, (res) -> {
            if (res.next()) {
                isExist.set(true);
            }
        });

        return isExist.get();
    }

}
