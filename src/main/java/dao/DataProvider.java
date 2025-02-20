package dao;

import java.sql.Connection;
import java.util.List;

public interface DataProvider<entityType, idType> {
    List<entityType> getAll(int page, int pageSize);
    entityType getById(idType id);
    void add(entityType entity);
    void update(idType id, entityType entity);
    void delete(idType id);
}
