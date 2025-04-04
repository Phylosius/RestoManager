package hei.phylosius.restomanager.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetHandler {
    void execute(ResultSet resultSet) throws SQLException;
}
