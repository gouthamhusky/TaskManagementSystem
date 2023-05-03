package edu.northeastern.csye.tms.dao;

import edu.northeastern.csye.tms.entity.User;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 A data access object class that provides CRUD operations for User entities.
 @author Goutham K
 */
@Repository
@Log4j2
public class UserDAO implements GenericDAO<User> {

    @PersistenceContext
    private EntityManager entityManager;

    private final ThreadLocal<EntityManager> threadLocalEntityManager = new ThreadLocal<>();

    /**
     Persists a User entity to the database.
     @param user The User object to persist
     @throws IllegalArgumentException if user is null
     @throws PersistenceException if there is an error persisting the entity
     */
    @Override
    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public void persist(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Cannot persist a null User object.");
        }
        try {
            getEntityManager().persist(user);
            log.info("User {} persisted successfully", user.getId());

        } catch (Exception e) {
            log.error("Error persisting User {}", user.getId());
            throw new PersistenceException("Error persisting User entity: " + e.getMessage(), e);
        }
    }

    /**
     Updates an existing User entity in the database.
     @param user The User object to update
     @throws IllegalArgumentException if user is null
     @throws PersistenceException if there is an error updating the entity
     */
    @Override
    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public void update(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Cannot update a null User object.");
        }
        try {
            getEntityManager().merge(user);
            log.info("User {} updated successfully", user.getId());
        } catch (Exception e) {
            log.error("Error updating User {}", user.getId());
            throw new PersistenceException("Error updating User entity: " + e.getMessage(), e);
        }
    }

    /**
     Deletes a User entity from the database.
     @param id The ID of the User entity to delete
     @throws IllegalArgumentException if id is null
     @throws EntityNotFoundException if a User entity with the given id does not exist
     @throws PersistenceException if there is an error deleting the entity
     */
    @Override
    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public void delete(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot delete a User object with a null ID.");
        }
        User user = getEntityManager().find(User.class, id);
        if (user == null) {
            throw new EntityNotFoundException("User entity with ID " + id + " not found.");
        }
        try {
            getEntityManager().remove(user);
            log.info("User {} deleted successfully", id);
        } catch (Exception e) {
            log.error("Error deleting User {}", id);
            throw new PersistenceException("Error deleting User entity: " + e.getMessage(), e);
        }
    }

    /**
     Retrieves a User entity from the database by ID.
     @param id The ID of the User entity to retrieve
     @return The User object with the given ID
     @throws IllegalArgumentException if id is null
     @throws EntityNotFoundException if a User entity with the given id does not exist
     */
    @Override
    @Cacheable(value = "users", key = "#id")
    public User get(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot get a User object with a null ID.");
        }
        User user = getEntityManager().find(User.class, id);
        log.info("User {} retrieved successfully", id);
        if (user == null) {
            log.error("User {} does not exist", id);
            throw new EntityNotFoundException("User entity with ID " + id + " not found.");
        }
        return user;
    }

    /**
     Retrieves a User entity from the database by username.
     @param userName The username of the User entity to retrieve
     @return The User object with the given username
     @throws EntityNotFoundException if a User entity with the given username does not exist
     */
    @Cacheable(value = "users", key = "#userName")
    public User get(String userName){
        TypedQuery<User> query = getEntityManager().createQuery("FROM User WHERE userName=:username", User.class);
        query.setParameter("username", userName);

        User user =  query.getSingleResult();
        log.info("User with username {} retrieved successfully", userName);
        if (user == null){
            log.error("User {} does not exist", userName);
            throw new EntityNotFoundException("User entity with username " + userName + "not found");
        }
        return user;
    }

    /**
     Retrieves a list of all User entities in the database.
     @return A List of all User entities in the database
     @throws PersistenceException if there is an error retrieving the list of entities
     */
    @Cacheable(value = "users", key = "#root.methodName")
    public List<User> getUsers(){
        try {
            TypedQuery<User> query = getEntityManager().createQuery("FROM User",  User.class);
            List<User> userList = query.getResultList();
            log.info("Retrieved {} users successfully", userList.size());
            return userList;
        } catch (Exception e) {
            log.error("Error getting list of users");
            throw new PersistenceException("Error getting list of User entities: " + e.getMessage(), e);
        }
    }

    /**
     Retrieves the total number of User entities in the database.
     @return The total number of User entities in the database
     @throws PersistenceException if there is an error retrieving the count of entities
     */
    @Cacheable(value = "users", key = "#root.methodName")
    public int getUserCount(){
        try {
            int count = getUsers().size();
            log.info("Retrieved {} users successfully", count);
            return count;
        } catch (Exception e) {
            throw new PersistenceException("Error getting count of User entities: " + e.getMessage(), e);
        }
    }

    private EntityManager getEntityManager(){
        if (threadLocalEntityManager.get() == null)
            threadLocalEntityManager.set(entityManager);

        return threadLocalEntityManager.get();
    }

    @PreDestroy
    private void cleanup(){
        EntityManager entityManager = threadLocalEntityManager.get();
        if (entityManager != null && entityManager.isOpen()){
            entityManager.close();
        }
        threadLocalEntityManager.remove();
    }
}