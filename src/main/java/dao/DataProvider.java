package dao;

import java.sql.Connection;
import java.util.List;

public interface DataProvider<entityType, idType> {
    List<entityType> getAll(Connection conn, int page, int pageSize);
    entityType getById(Connection conn, idType id);
    void add(Connection conn, entityType entity);
    void update(Connection conn, entityType entity);
    void delete(Connection conn, idType id);
}
