package edu.northeastern.csye.tms.dao;

import edu.northeastern.csye.tms.entity.User;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO implements GenericDAO<User> {

    @PersistenceContext
    private EntityManager entityManager;

    private ThreadLocal<EntityManager> threadLocalEntityManager = new ThreadLocal<>();

    @Override
    @Transactional
    public void persist(User user) {
        getEntityManager().persist(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        getEntityManager().merge(user);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        User user = getEntityManager().find(User.class, id);
        getEntityManager().remove(user);
    }

    @Override
    public User get(Integer id) {
        return getEntityManager().find(User.class, id);
    }

    public List<User> getUsers(){
        TypedQuery<User> query = getEntityManager().createQuery("FROM User", User.class);
        List<User> users = query.getResultList();
        return users;
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