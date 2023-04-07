package edu.northeastern.csye.tms.dao;

import edu.northeastern.csye.tms.entity.Pulse;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PulseDAO implements GenericDAO<Pulse>{

    @PersistenceContext
    private EntityManager entityManager;

    private ThreadLocal<EntityManager> threadLocalEntityManager = new ThreadLocal<>();


    @Override
    @Transactional
    public void persist(Pulse pulse) {
        getEntityManager().persist(pulse);
    }

    @Override
    @Transactional
    public void update(Pulse pulse) {
        getEntityManager().merge(pulse);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Pulse pulse = getEntityManager().find(Pulse.class, id);
        getEntityManager().remove(pulse);
    }

    @Override
    public Pulse get(Integer id) {
        return entityManager.find(Pulse.class, id);
    }

    public List<Pulse> getPulses(){
        TypedQuery<Pulse> query = getEntityManager().createQuery("FROM Pulse",  Pulse.class);
        List<Pulse> results = query.getResultList();
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
