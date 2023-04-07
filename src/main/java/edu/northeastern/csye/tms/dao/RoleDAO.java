package edu.northeastern.csye.tms.dao;

import edu.northeastern.csye.tms.entity.Role;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDAO implements GenericDAO<Role>{

    @PersistenceContext
    private EntityManager entityManager;

    private ThreadLocal<EntityManager> threadLocalEntityManager = new ThreadLocal<>();


    @Override
    @Transactional
    public void persist(Role role) {
        getEntityManager().persist(role);
    }

    @Override
    @Transactional
    public void update(Role role) {
        getEntityManager().merge(role);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Role role = getEntityManager().find(Role.class, id);
        getEntityManager().remove(role);
    }

    @Override
    @Transactional
    public Role get(Integer id) {
        Role role = getEntityManager().find(Role.class, id);
        return role;
    }

    public List<Role> getRoles(){
        TypedQuery<Role> query = getEntityManager().createQuery("FROM Role",  Role.class);
        List<Role> results = query.getResultList();
        return results;
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
