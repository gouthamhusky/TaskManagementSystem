package edu.northeastern.csye.tms.dao;

import edu.northeastern.csye.tms.entity.Pulse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PulseDAO implements GenericDAO<Pulse>{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void persist(Pulse pulse) {
        entityManager.persist(pulse);
    }

    @Override
    @Transactional
    public void update(Pulse pulse) {
        entityManager.merge(pulse);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Pulse pulse = entityManager.find(Pulse.class, id);
        entityManager.remove(pulse);
    }

    @Override
    public Pulse get(Integer id) {
        return entityManager.find(Pulse.class, id);
    }

    public List<Pulse> getPulses(){
        TypedQuery<Pulse> query = entityManager.createQuery("FROM Pulse",  Pulse.class);
        List<Pulse> results = query.getResultList();
        return results;
    }
}
