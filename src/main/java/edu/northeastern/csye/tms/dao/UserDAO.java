package edu.northeastern.csye.tms.dao;

import edu.northeastern.csye.tms.config.HibernateSessionProvider;
import edu.northeastern.csye.tms.entity.User;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public abstract class UserDAO implements GenericDAO<User> {

    private HibernateSessionProvider sessionProvider;

    @Autowired
    public UserDAO(HibernateSessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

    @Override
    public void save(User user) {
        sessionProvider.getSession().persist(user);
        sessionProvider.close();
    }

    @Override
    public void update(User user) {
        sessionProvider.getSession().merge(user);
        sessionProvider.close();
    }

    @Override
    public void delete(User user) {
        sessionProvider.getSession().remove(user);
        sessionProvider.close();
    }

    @Override
    public User getById(Integer id) {
        User result = sessionProvider.getSession().get(User.class, id);
        sessionProvider.close();
        return result;
    }

    public List<User> getUsers(){
        TypedQuery<User> query = sessionProvider.getSession().createQuery("FROM users", User.class);
        List<User> users = query.getResultList();
        sessionProvider.close();
        return users;
    }
}
