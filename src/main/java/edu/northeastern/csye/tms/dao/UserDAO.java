package edu.northeastern.csye.tms.dao;

import edu.northeastern.csye.tms.config.HibernateSessionManager;
import edu.northeastern.csye.tms.entity.User;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO implements GenericDAO<User> {

    private final HibernateSessionManager sessionManager;

    @Autowired
    public UserDAO(HibernateSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    @Transactional
    public void persist(User user) {
        sessionManager.getSession().persist(user);
        sessionManager.close();
    }

    @Override
    @Transactional
    public void update(User user) {
        sessionManager.getSession().merge(user);
        sessionManager.close();
    }

    @Override
    @Transactional
    public void delete(User user) {
        sessionManager.getSession().remove(user);
        sessionManager.close();
    }

    @Override
    public User get(Integer id) {
        User result = sessionManager.getSession().get(User.class, id);
        sessionManager.close();
        return result;
    }

    public List<User> getUsers(){
        TypedQuery<User> query = sessionManager.getSession().createQuery("FROM users", User.class);
        List<User> users = query.getResultList();
        sessionManager.close();
        return users;
    }
}
