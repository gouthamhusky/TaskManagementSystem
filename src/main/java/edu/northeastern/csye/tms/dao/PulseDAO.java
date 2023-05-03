package edu.northeastern.csye.tms.dao;

import edu.northeastern.csye.tms.entity.Pulse;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 A data access object class that provides CRUD operations for Pulse entities.
 @author Goutham K
 */
@Repository
@Log4j2
public class PulseDAO implements GenericDAO<Pulse>{

    @PersistenceContext
    private EntityManager entityManager;

    private ThreadLocal<EntityManager> threadLocalEntityManager = new ThreadLocal<>();


    /**
     Persists a Pulse entity to the database.
     @param pulse The Pulse object to persist
     @throws IllegalArgumentException if pulse is null
     @throws PersistenceException if there is an error persisting the entity
     */
    @Override
    @Transactional
    @CacheEvict(value = "pulses", allEntries = true)
    public void persist(Pulse pulse){
        if (pulse == null) {
            throw new IllegalArgumentException("Cannot persist a null Pulse object.");
        }
        try {
            getEntityManager().persist(pulse);
            log.info("Successfully persisted Pulse entity with ID {}", pulse.getId());
        } catch (Exception e) {
            log.error("Error persisting Pulse entity {}", e.getMessage());
            throw new PersistenceException("Error persisting Pulse entity: " + e.getMessage(), e);
        }
    }


    /**
     Updates an existing Pulse entity in the database.
     @param pulse The Pulse object to update
     @throws IllegalArgumentException if pulse is null
     @throws PersistenceException if there is an error updating the entity
     */
    @Override
    @Transactional
    @CacheEvict(value = "pulses", allEntries = true)
    public void update(Pulse pulse){
        if (pulse == null) {
            throw new IllegalArgumentException("Cannot update a null Pulse object.");
        }
        try {
            getEntityManager().merge(pulse);
            log.info("Successfully updated Pulse entity with ID {}", pulse.getId());
        } catch (Exception e) {
            log.error("Error updating Pulse entity with ID {}", pulse.getId());
            throw new PersistenceException("Error updating Pulse entity: " + e.getMessage(), e);
        }
    }

    /**
     Deletes a Pulse entity from the database.
     @param id The ID of the Pulse entity to delete
     @throws IllegalArgumentException if id is null
     @throws EntityNotFoundException if a Pulse entity with the given id does not exist
     @throws PersistenceException if there is an error deleting the entity
     */
    @Override
    @Transactional
    @CacheEvict(value = "pulses", allEntries = true)
    public void delete(Integer id){
        if (id == null) {
            throw new IllegalArgumentException("Cannot delete a Pulse object with a null ID.");
        }
        Pulse pulse = getEntityManager().find(Pulse.class, id);
        if (pulse == null) {
            log.error("Pulse entity with ID {} not found", id);
            throw new EntityNotFoundException("Pulse entity with ID " + id + " not found.");
        }
        try {
            Query query = getEntityManager().createQuery("delete from Pulse where id=:id");
            query.setParameter("id", id);
            query.executeUpdate();
            log.info("Successfully deleted Pulse entity with ID {}", pulse.getId());
        } catch (Exception e) {
            log.error("Error deleting Pulse entity with ID {}", pulse.getId());
            throw new PersistenceException("Error deleting Pulse entity: " + e.getMessage(), e);
        }
    }

    /**
     Retrieves a Pulse entity from the database by ID.
     @param id The ID of the Pulse entity to retrieve
     @return The Pulse object with the given ID
     @throws IllegalArgumentException if id is null
     @throws EntityNotFoundException if a Pulse entity with the given id does not exist
     */
    @Override
    @Cacheable(value = "pulses", key = "#id")
    public Pulse get(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot get a Pulse object with a null ID.");
        }
        Pulse pulse = getEntityManager().find(Pulse.class, id);
        if (pulse == null) {
            log.error("Pulse entity with ID {} not found", id);
            throw new EntityNotFoundException("Pulse entity with ID " + id + " not found.");
        }
        log.info("Successfully retrieved Pulse entity with ID {}", pulse.getId());
        return pulse;
    }

    /**
     Retrieves a list of all Pulse entities in the database.
     @return A List of all Pulse entities in the database
     @throws PersistenceException if there is an error retrieving the list of entities
     */
    @Cacheable(value = "pulses", key = "#root.methodName")
    public List<Pulse> getPulses(){
        try {
            TypedQuery<Pulse> query = getEntityManager().createQuery("FROM Pulse",  Pulse.class);
            List<Pulse> pulses = query.getResultList();
            log.info("Successfully retrieved {} Pulse entities", pulses.size());
            return pulses;
        } catch (Exception e) {
            log.error("Error getting list of Pulse entities");
            throw new PersistenceException("Error getting list of Pulse entities: " + e.getMessage(), e);
        }
    }

    /**
     Retrieves the total number of Pulse entities in the database.
     @return The total number of Pulse entities in the database
     @throws PersistenceException if there is an error retrieving the count of entities
     */
    @Cacheable(value = "pulses", key = "#root.methodName")
    public int getPulseCount(){
        try {
            int count = getPulses().size();
            log.info("Successfully retrieved count of Pulse entities: {}", count);
            return count;
        } catch (Exception e) {
            log.error("Error getting count of Pulse entities");
            throw new PersistenceException("Error getting count of Pulse entities: " + e.getMessage(), e);
        }
    }

    private EntityManager getEntityManager() {
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