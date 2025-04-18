package hei.phylosius.restomanager.Repository;

import hei.phylosius.restomanager.mappers.DishMapper;
import hei.phylosius.restomanager.model.Criteria;
import hei.phylosius.restomanager.model.Dish;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
@Repository
public class DishDAO implements DataProvider<Dish, String> {

    private DataSource dataSource;
    private DishMapper dishMapper;

    public List<Dish> getAllByCriteria(List<Criteria> criteria, int page, int pageSize) {
        return getAllByCriteria(dataSource.getConnection(), criteria, page, pageSize);
    }

    public List<Dish> getAll() {
        List<Dish> dishes = new ArrayList<>();

        String sql = "SELECT id, name, unit_price FROM dish";
        BaseDAO.executeQuery(dataSource.getConnection(), sql, List.of(), resultSet -> {
            while (resultSet.next()) {
                dishes.add(dishMapper.toEntity(resultSet));
            }
        });

        return dishes;
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

    public static List<Dish> getAll(Connection conn, Integer page, Integer pageSize) {

        if (page < 1) {
            throw new IllegalPageValueException("The value of the parameter page should be greater than 0.");
        }

        if (pageSize < 1) {
            throw new IllegalPageSizeValueException("The value of the parameter pageSize should be greater than 0.");
        }

        List<Dish> dishes = new ArrayList<>();

        String sql = "SELECT id, name, unit_price FROM dish ORDER BY id ASC LIMIT ? OFFSET ?";
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

                dishes.add(dish);
            }

        });

        return dishes;
    }

    public static List<Dish> getAllByCriteria(Connection conn, List<Criteria> criteria, int page, int pageSize) {
        List<Dish> dishes = new ArrayList<>();

        String sql = "SELECT id, name, unit_price FROM dish WHERE 1=1 ";

        BaseDAO.getAllByCriteria(conn, criteria, page, pageSize, sql, (r) -> {
            while (r.next()) {
                Dish dish = new Dish();

                dish.setId(r.getString("id"));
                dish.setName(r.getString("name"));
                dish.setUnitPrice(r.getDouble("unit_price"));

                dishes.add(dish);
            }
        });

        return dishes;
    }

    public static Dish getById(Connection conn, String id) {
        Dish dish = new Dish();

        String sql = "SELECT id, name, unit_price FROM dish WHERE id = ?";
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
        if (!isExist(conn, entity.getId())) {
            add(conn, entity);
        } else {
            update(conn, entity.getId(), entity);
        }
    }

    public static void add(Connection conn, Dish entity) {

        String sql = "INSERT INTO dish(id, name, unit_price) VALUES (?, ?, ?)";
        List<Object> params = List.of(
                entity.getId(),
                entity.getName(),
                entity.getUnitPrice()
        );

        BaseDAO.executeUpdate(conn, sql, params);
        MakeUpDAO.saveAll(conn, entity.getId(), entity.getMakeUps());

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
        MakeUpDAO.saveAll(conn, entity.getId(), entity.getMakeUps());

    }

    public static Boolean isExist(Connection conn, String id) {
        AtomicReference<Boolean> exist = new AtomicReference<>(false);

        String sql = "SELECT name FROM dish WHERE id = ?";
        List<Object> params = List.of(id);

        BaseDAO.executeQuery(conn, sql, params, (r) -> {
            if (r.next()) {
                exist.set(true);
            }
        });

        return exist.get();
    }

    public static void delete(Connection conn, String id) {

        String sql = "DELETE FROM dish WHERE id = ?";
        List<Object> params = List.of(id);

        BaseDAO.executeUpdate(conn, sql, params);

    }
}
