package edu.northeastern.csye.tms.dao;

import edu.northeastern.csye.tms.entity.Pulse;
import edu.northeastern.csye.tms.entity.Role;
import edu.northeastern.csye.tms.entity.Task;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 A data access object class that provides CRUD operations for User entities.
 @author Goutham K
 */
@Repository
@Log4j2
public class TaskDAO implements GenericDAO<Task>{

    @PersistenceContext
    private EntityManager entityManager;

    private ThreadLocal<EntityManager> threadLocalEntityManager = new ThreadLocal<>();

    @Autowired
    private PulseDAO pulseDAO;

    /**
     Persists a Task entity to the database.
     @param task The Task object to persist
     @throws IllegalArgumentException if task is null
     @throws PersistenceException if there is an error persisting the entity
     */
    @Override
    @Transactional
    @CacheEvict(value = "tasks", allEntries = true)
    public void persist(Task task) {
        if (task == null) {

            throw new IllegalArgumentException("Cannot persist a null Task object.");
        }
        try {
            getEntityManager().persist(task);
      log.info("Task {} persisted successfully", task.getId());
        } catch (Exception e) {
            log.error("Error persisting Task {}", task.getId());
            throw new PersistenceException("Error persisting Task entity: " + e.getMessage(), e);
        }
    }

    /**
     Updates an existing Task entity in the database.
     @param task The Task object to update
     @throws IllegalArgumentException if task is null
     @throws PersistenceException if there is an error updating the entity
     */
    @Override
    @Transactional
    @CacheEvict(value = "tasks", allEntries = true)
    public void update(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Cannot update a null Task object.");
        }
        try {
            getEntityManager().merge(task);
            log.info("Task {} updated successfully", task.getId());
        } catch (Exception e) {
            log.error("Error updating task {}", task.getId());
            throw new PersistenceException("Error updating task entity: " + e.getMessage(), e);
        }
    }

    /**
     Deletes a Task entity from the database.
     @param id The ID of the Task entity to delete
     @throws IllegalArgumentException if id is null
     @throws EntityNotFoundException if a Task entity with the given id does not exist
     @throws PersistenceException if there is an error deleting the entity
     */
    @Override
    @Transactional
    @CacheEvict(value = "tasks", allEntries = true)
    public void delete(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot delete a Task object with a null ID.");
        }
        Task task = getEntityManager().find(Task.class, id);
        if (task == null) {
            throw new EntityNotFoundException("Task entity with ID " + id + " not found.");
        }
        try {
            for (Pulse pulse : task.getPulses()) {
                pulseDAO.delete(pulse.getId());
            }
            Query query = getEntityManager().createQuery("delete from Task where id=:id");
            query.setParameter("id", id);
            query.executeUpdate();
            log.info("Task entity with ID {} deleted successfully.", id);
        } catch (Exception e) {
            log.error("Error deleting Task entity with ID {}", task.getId());
            throw new PersistenceException("Error deleting Task entity: " + e.getMessage(), e);
        }
    }

    /**
     Retrieves a Task entity from the database by ID.
     @param id The ID of the Task entity to retrieve
     @return The Task object with the given ID
     @throws IllegalArgumentException if id is null
     @throws EntityNotFoundException if a Task entity with the given id does not exist
     */
    @Override
    @Transactional
    @Cacheable(value = "tasks", key = "#id")
    public Task get(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot get a Task object with a null ID.");
        }
        Task task = getEntityManager().find(Task.class, id);
        if (task == null) {
            throw new EntityNotFoundException("Task entity with ID " + id + " not found.");
        }
        log.info("Retrieved Task entity with ID {}", id );
        return task;
    }

    /**
     Retrieves a list of all Task entities in the database.
     @return A List of all Task entities in the database
     @throws PersistenceException if there is an error retrieving the list of entities
     */
    @Cacheable(value = "tasks", key = "#root.methodName")
    public List<Task> getTasks(){
        try {
            TypedQuery<Task> query = getEntityManager().createQuery("FROM Task",  Task.class);
            List<Task> tasks = query.getResultList();
            log.info("Retrieved {} Task entities.", tasks.size());
            return tasks;
        } catch (Exception e) {
            log.error("Error getting list of Task entities");
            throw new PersistenceException("Error getting list of Task entities: " + e.getMessage(), e);
        }
    }

    /**
     Retrieves the total number of Task entities in the database.
     @return The total number of Task entities in the database
     @throws PersistenceException if there is an error retrieving the count of entities
     */
    @Cacheable(value = "tasks", key = "#root.methodName")
    public int getTaskCount(){
        try {
            int count = getTasks().size();
            log.info("Retrieved count of {} Task entities.", count);
            return count;
        } catch (Exception e) {
            log.error("Error getting count of Task entities");
            throw new PersistenceException("Error getting count of Task entities: " + e.getMessage(), e);
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