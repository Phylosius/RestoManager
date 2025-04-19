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

    public static int addAll(
            Connection conn, String dishID, List<MakeUp> makeUps
    ) 
    {
        int saved;
//
//        if (!DishDAO.isExist(conn, dishID)) {
//            throw new DishNotFoundException(String.format("Dish of id %s not found", dishID));
//        }
//
//        makeUps.forEach(makeUp -> {
//            if (!IngredientDAO.isExist(conn, makeUp.getIngredient().getId())) {
//                throw new IngredientNotFoundException(String.format("Ingredient of id %s not found", makeUp.getIngredient().getId()));
//            }
//
//            if (isExist(conn, dishID, makeUp.getIngredient().getId())) {
//                System.out.println("error____");
//                throw new IllegalArgumentException(
//                        String.format("Make-up of Dish %s with Ingredient %s already exist", dishID,
//                                makeUp.getIngredient().getId()));
//            }
//        });

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

    public static Boolean isExist(Connection conn, String dishId, String ingredientId){
        AtomicReference<Boolean> isExist = new AtomicReference<>(false);

        String sql = "SELECT dish_id FROM make_up WHERE dish_id = ? AND ingredient_id = ?";
        List<Object> params = List.of(dishId, ingredientId);

        BaseDAO.executeQuery(conn, sql, params, (res) -> {
            if (res.next()) {
                isExist.set(true);
            }
        });

        return isExist.get();
    }

}
