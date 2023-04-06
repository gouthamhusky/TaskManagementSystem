package edu.northeastern.csye.tms.dao;

public interface GenericDAO<T> {
    public void persist(T entity);

    public void update(T entity);

    public void delete(T entity);

    public T get(Integer id);
}
