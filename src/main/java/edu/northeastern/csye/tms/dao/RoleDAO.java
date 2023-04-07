package edu.northeastern.csye.tms.dao;

import edu.northeastern.csye.tms.entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDAO implements GenericDAO<Role>{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void persist(Role role) {
        entityManager.persist(role);
    }

    @Override
    @Transactional
    public void update(Role role) {
        entityManager.merge(role);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Role role = entityManager.find(Role.class, id);
        entityManager.remove(role);
    }

    @Override
    @Transactional
    public Role get(Integer id) {
        Role role = entityManager.find(Role.class, id);
        return role;
    }

    public List<Role> getRoles(){
        TypedQuery<Role> query = entityManager.createQuery("FROM Role",  Role.class);
        List<Role> results = query.getResultList();
        return results;
    }
}
