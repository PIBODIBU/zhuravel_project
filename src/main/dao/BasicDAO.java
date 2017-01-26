package main.dao;

import java.util.ArrayList;
import java.util.List;

public interface BasicDAO<T> {
    List<T> getAll();

    String toJson(ArrayList<T> models);

    String toJson(T model);

    T get(Integer id);

    Integer insert(T model);

    void update(T model);

    void delete(T model);

    void insertOrUpdate(T model);
}
