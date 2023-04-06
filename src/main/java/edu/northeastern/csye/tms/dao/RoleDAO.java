package edu.northeastern.csye.tms.dao;

import edu.northeastern.csye.tms.config.HibernateSessionManager;
import edu.northeastern.csye.tms.entity.Role;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDAO implements GenericDAO<Role>{

    private final HibernateSessionManager sessionManager;

    @Autowired
    public RoleDAO(HibernateSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    @Transactional
    public void persist(Role role) {
        sessionManager.getSession().save(role);
        sessionManager.close();
    }

    @Override
    @Transactional
    public void update(Role role) {
        sessionManager.getSession().merge(role);
        sessionManager.close();
    }

    @Override
    @Transactional
    public void delete(Role role) {
        sessionManager.getSession().remove(role);
        sessionManager.close();
    }

    @Override
    public Role get(Integer id) {
        Role role = sessionManager.getSession().find(Role.class, id);
        sessionManager.close();
        return role;
    }

    public List<Role> getRoles(){
        TypedQuery<Role> query = sessionManager.getSession().createQuery("FROM Role",  Role.class);
        List<Role> results = query.getResultList();
        sessionManager.close();
        return results;
    }
}
