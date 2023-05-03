package edu.northeastern.csye.tms.dao;


import edu.northeastern.csye.tms.entity.Marker;

/**

 The GenericDAO interface defines the basic CRUD (Create, Retrieve, Update, Delete)
 operations for a data access object that works with entities marked with the Marker interface.
 @param <T> The type of entity that this DAO works with.

 @author Goutham K
 */
public interface GenericDAO<T extends Marker> {

    /**
     Persists the given entity to the database.
     @param entity The entity to persist.
     */
    public void persist(T entity);

    /**
     Updates the given entity in the database.
     @param entity The entity to update.
     */
    public void update(T entity);

    /**
     Deletes the entity with the specified ID from the database.
     @param id The ID of the entity to delete.
     */
    public void delete(Integer id);

    /**
     Retrieves the entity with the specified ID from the database.
     @param id The ID of the entity to retrieve.
     @return The retrieved entity, or null if no entity with the specified ID exists.
     */
    public T get(Integer id);
}