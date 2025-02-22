package dao;

import java.util.List;

public interface DataProvider<entityType, idType> {
    List<entityType> getAll(int page, int pageSize);
    entityType getById(idType id);
    void save(entityType entity);
    void update(idType id, entityType entity);
    void delete(idType id);
}
