    package edu.northeastern.csye.tms.dao;

    import edu.northeastern.csye.tms.entity.Role;
    import jakarta.annotation.PreDestroy;
    import jakarta.persistence.*;
    import jakarta.transaction.Transactional;
    import java.util.List;
    import lombok.extern.log4j.Log4j2;
    import org.springframework.cache.annotation.CacheEvict;
    import org.springframework.cache.annotation.Cacheable;
    import org.springframework.stereotype.Repository;

    /**
     A data access object class that provides CRUD operations for Role entities.
     @author Goutham K
     */
    @Repository
    @Log4j2
    public class RoleDAO implements GenericDAO<Role>{

        @PersistenceContext
        private EntityManager entityManager;

        private ThreadLocal<EntityManager> threadLocalEntityManager = new ThreadLocal<>();


        /**
         Persists a Role entity to the database.
         @param role The Task object to persist
         @throws IllegalArgumentException if role is null
         @throws PersistenceException if there is an error persisting the entity
         */
        @Override
        @Transactional
        @CacheEvict(value = "roles", allEntries = true)
        public void persist(Role role) {
            if (role == null) {
                throw new IllegalArgumentException("Cannot persist a null Role object.");
            }
            try {
                getEntityManager().persist(role);
                log.info("Role entity with ID {} persisted: ", role.getId());
            } catch (Exception e) {
                log.error("Error persisting Role entity with ID {}", role.getId());
                throw new PersistenceException("Error persisting Role entity: " + e.getMessage(), e);
            }
        }

        /**
         Updates an existing Role entity in the database.
         @param role The Task object to update
         @throws IllegalArgumentException if role is null
         @throws PersistenceException if there is an error updating the entity
         */
        @Override
        @Transactional
        @CacheEvict(value = "roles", allEntries = true)
        public void update(Role role) {
            if (role == null) {
                throw new IllegalArgumentException("Cannot update a null Role object.");
            }
            try {
                getEntityManager().merge(role);
                log.info("Role entity with ID {} updated", role.getId());
            } catch (Exception e) {
                log.error("Error updating Role entity with ID {}", role.getId());
                throw new PersistenceException("Error updating Role entity: " + e.getMessage(), e);
            }
        }

        /**
         Deletes a Role entity from the database.
         @param id The ID of the Role entity to delete
         @throws IllegalArgumentException if id is null
         @throws EntityNotFoundException if a Role entity with the given id does not exist
         @throws PersistenceException if there is an error deleting the entity
         */
        @Override
        @Transactional
        @CacheEvict(value = "roles", allEntries = true)
        public void delete(Integer id) {
            if (id == null) {
                throw new IllegalArgumentException("Cannot delete a Role object with a null ID.");
            }
            Role role = getEntityManager().find(Role.class, id);
            if (role == null) {
                throw new EntityNotFoundException("Pulse entity with ID " + id + " not found.");
            }
            try {
                getEntityManager().remove(role);
                log.info("Role entity deleted with ID: " + id);
            } catch (Exception e) {
                log.error("Error deleting Role entity with ID {}", role.getId());
                throw new PersistenceException("Error deleting Role entity: " + e.getMessage(), e);
            }
        }


        /**
         Retrieves a Role entity from the database by ID.
         @param id The ID of the Role entity to retrieve
         @return The Role object with the given ID
         @throws IllegalArgumentException if id is null
         @throws EntityNotFoundException if a Role entity with the given id does not exist
         */
        @Override
        @Cacheable(value = "roles", key = "#id")
        public Role get(Integer id) {
            if (id == null) {
                throw new IllegalArgumentException("Cannot get a Role object with a null ID.");
            }
            Role role = getEntityManager().find(Role.class, id);
            log.info("Retrieved Role entity with ID: " + id);
            if (role == null) {
                log.error("Role entity with ID {} not found", id);
                throw new EntityNotFoundException("Role entity with ID " + id + " not found.");
            }
            return role;
        }

        /**
         Retrieves a list of all Role entities in the database.
         @return A List of all Role entities in the database
         @throws PersistenceException if there is an error retrieving the list of entities
         */
        @Cacheable(value = "roles", key = "#root.methodName")
        public List<Role> getRoles(){
            try {
                TypedQuery<Role> query = getEntityManager().createQuery("FROM Role",  Role.class);
                List<Role> roles = query.getResultList();
                log.info("Retrieved list of Role entities with size: " + roles.size());
                return roles;
            } catch (Exception e) {
                log.error("Error getting list of Role entities");
                throw new PersistenceException("Error getting list of Role entities: " + e.getMessage(), e);
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