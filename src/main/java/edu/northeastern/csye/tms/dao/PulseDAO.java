package edu.northeastern.csye.tms.dao;

import edu.northeastern.csye.tms.config.HibernateSessionManager;
import edu.northeastern.csye.tms.entity.Pulse;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PulseDAO implements GenericDAO<Pulse>{

    private final HibernateSessionManager sessionManager;

    @Autowired
    public PulseDAO(HibernateSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    @Transactional
    public void persist(Pulse pulse) {
        sessionManager.getSession().persist(pulse);
        sessionManager.close();
    }

    @Override
    @Transactional
    public void update(Pulse pulse) {
        sessionManager.getSession().merge(pulse);
        sessionManager.close();
    }

    @Override
    @Transactional
    public void delete(Pulse pulse) {
        sessionManager.getSession().remove(pulse);
        sessionManager.close();
    }

    @Override
    public Pulse get(Integer id) {
        Pulse pulse = sessionManager.getSession().find(Pulse.class, id);
        sessionManager.close();
        return pulse;
    }

    public List<Pulse> getPulses(){
        TypedQuery<Pulse> query = sessionManager.getSession().createQuery("FROM Pulse",  Pulse.class);
        List<Pulse> results = query.getResultList();
        sessionManager.close();
        return results;
    }
}
