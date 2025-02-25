package dao;

import model.MakeUp;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MakeUpDAO{

    private final DataSource dataSource;

    public MakeUpDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public List<MakeUp> getAllByDishID(String dishID){
        return getAllByDishID(dataSource.getConnection(), dishID);
    }

    public int saveAll(String dishID, List<MakeUp> makeUpList){
        return saveAll(dataSource.getConnection(),dishID, makeUpList);
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

    public static int saveAll(Connection conn, String dishID, List<MakeUp> makeUps){
        int saved;

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

}
