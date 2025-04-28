package hei.phylosius.restomanager.Repository;

import hei.phylosius.restomanager.model.Criteria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseDAO {

    public static void executeQuery(Connection connection, String sql, List<Object> params, ResultSetHandler resultSetHandler) {

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSetHandler.execute(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getAllByCriteria(Connection connection, List<Criteria> criteria, String sql, ResultSetHandler resultSetHandler) {
        StringBuilder sqlBuilder = new StringBuilder(sql);

        for (Criteria c : criteria) {
            sqlBuilder.append(c.getSqlValue());
        }

        sql = sqlBuilder.toString();

        List<Object> params = List.of();

        executeQuery(connection, sql, params, resultSetHandler);
    }

    public static void getAllByCriteria(Connection connection, List<Criteria> criteria, Integer page, Integer pageSize, String sql, ResultSetHandler resultSetHandler) {
        StringBuilder sqlBuilder = new StringBuilder(sql);

        for (Criteria c : criteria) {
            sqlBuilder.append(c.getSqlValue());
        }

        List<Object> params = new ArrayList<>();

        if (page != null && pageSize != null) {
            sqlBuilder.append(" LIMIT ? OFFSET ?");
            sql = sqlBuilder.toString();
            params.addAll(List.of(pageSize, pageSize * (page - 1)));
        }

        executeQuery(connection, sql, params, resultSetHandler);
    }

    public static int executeUpdate(Connection connection, String sql, List<Object> params) {

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
