package hei.phylosius.restomanager.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetHandler {
    void execute(ResultSet resultSet) throws SQLException;
}
