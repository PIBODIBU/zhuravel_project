package main.dao;

import java.util.ArrayList;

public interface BasicDAO<T> {
    ArrayList<T> getAll();

    String toJson(ArrayList<T> models);

    String toJson(T model);

    T get(Integer id);

    Integer insert(T model);

    void update(T model);

    void delete(T model);

    void insertOrUpdate(T model);
}
