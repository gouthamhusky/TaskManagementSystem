package edu.northeastern.csye.tms.dao;

import edu.northeastern.csye.tms.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO implements GenericDAO<User> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void persist(User user) {
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public User get(Integer id) {
        return entityManager.find(User.class, id);
    }

    public List<User> getUsers(){
        TypedQuery<User> query = entityManager.createQuery("FROM User", User.class);
        List<User> users = query.getResultList();
        return users;
    }
}