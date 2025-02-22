package dao;

import model.Dish;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class DishDAO implements DataProvider<Dish, String> {

    private final DataSource dataSource;

    public DishDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Dish> getAll(int page, int pageSize) {
        return getAll(dataSource.getConnection(), page, pageSize);
    }

    @Override
    public Dish getById(String id) {
        return getById(dataSource.getConnection(), id);
    }

    @Override
    public void save(Dish entity) {
        save(dataSource.getConnection(), entity);
    }

    @Override
    public void update(String id, Dish entity) {
        update(dataSource.getConnection(), id, entity);
    }

    @Override
    public void delete(String id) {
        delete(dataSource.getConnection(), id);
    }

    public static List<Dish> getAll(Connection conn, int page, int pageSize) {
        List<Dish> dishs = new ArrayList<>();

        String sql = "SELECT * FROM dish ORDER BY id ASC LIMIT ? OFFSET ?";
        List<Object> params = List.of(pageSize, pageSize * (page - 1));

        BaseDAO.executeQuery(conn, sql, params, (r) -> {
            while (r.next()) {
                Dish dish = new Dish();

                dish.setId(r.getString("id"));
                dish.setName(r.getString("name"));
                dish.setUnitPrice(r.getDouble("unit_price"));
                dish.setMakeUps(
                        MakeUpDAO.getAllByDishID(conn, dish.getId())
                );

                dishs.add(dish);
            }

        });

        return dishs;
    }

    public static Dish getById(Connection conn, String id) {
        Dish dish = new Dish();

        String sql = "SELECT * FROM dish WHERE id = ?";
        List<Object> params = List.of(id);

        BaseDAO.executeQuery(conn, sql, params, (r) -> {
            if (r.next()) {
                dish.setId(r.getString("id"));
                dish.setName(r.getString("name"));
                dish.setUnitPrice(r.getDouble("unit_price"));
                dish.setMakeUps(
                        MakeUpDAO.getAllByDishID(conn, dish.getId())
                );
            }
        });

        return dish;
    }

    public static void save(Connection conn, Dish entity) {

        String sql = "INSERT INTO dish(id, name, unit_price) VALUES (?, ?, ?)";
        List<Object> params = List.of(
                entity.getId(),
                entity.getName(),
                entity.getUnitPrice()
        );

        BaseDAO.executeUpdate(conn, sql, params);
        MakeUpDAO.saveAll(conn, entity.getMakeUps());

    }

    public static void update(Connection conn, String id,  Dish entity) {

        String sql = "UPDATE dish SET name = ?, unit_price = ? WHERE id = ?";
        List<Object> params = List.of(
                entity.getName(),
                entity.getUnitPrice(),
                id
        );

        BaseDAO.executeUpdate(conn, sql, params);
        MakeUpDAO.deleteByDishID(conn, id);
        MakeUpDAO.saveAll(conn, entity.getMakeUps());

    }

    public static void delete(Connection conn, String id) {

        String sql = "DELETE FROM dish WHERE id = ?";
        List<Object> params = List.of(id);

        BaseDAO.executeUpdate(conn, sql, params);

    }
}
