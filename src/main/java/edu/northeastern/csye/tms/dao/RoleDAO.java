package edu.northeastern.csye.tms.dao;

import edu.northeastern.csye.tms.config.HibernateSessionProvider;
import edu.northeastern.csye.tms.entity.Role;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDAO implements GenericDAO<Role>{

    private HibernateSessionProvider sessionProvider;

    @Autowired
    public RoleDAO(HibernateSessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

    @Override
    @Transactional
    public void save(Role role) {
        sessionProvider.getSession().save(role);
        sessionProvider.close();
    }

    @Override
    public void update(Role role) {
        sessionProvider.getSession().merge(role);
        sessionProvider.close();
    }

    @Override
    public void delete(Role role) {
        sessionProvider.getSession().remove(role);
        sessionProvider.close();
    }

    @Override
    public Role getById(Integer id) {
        Role role = sessionProvider.getSession().find(Role.class, id);
        sessionProvider.close();
        return role;
    }

    public List<Role> getRoles(){
        TypedQuery<Role> query = sessionProvider.getSession().createQuery("FROM roles",  Role.class);
        List<Role> results = query.getResultList();
        sessionProvider.close();
        return results;
    }
}
