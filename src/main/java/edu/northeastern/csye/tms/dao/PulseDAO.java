package edu.northeastern.csye.tms.dao;

import edu.northeastern.csye.tms.config.HibernateSessionProvider;
import edu.northeastern.csye.tms.entity.Pulse;
import edu.northeastern.csye.tms.entity.Role;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PulseDAO implements GenericDAO<Pulse>{

    private HibernateSessionProvider sessionProvider;

    @Autowired
    public PulseDAO(HibernateSessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

    @Override
    public void save(Pulse pulse) {
        sessionProvider.getSession().persist(pulse);
        sessionProvider.close();
    }

    @Override
    public void update(Pulse pulse) {
        sessionProvider.getSession().merge(pulse);
        sessionProvider.close();
    }

    @Override
    public void delete(Pulse pulse) {
        sessionProvider.getSession().remove(pulse);
        sessionProvider.close();
    }

    @Override
    public Pulse getById(Integer id) {
        Pulse pulse = sessionProvider.getSession().find(Pulse.class, id);
        sessionProvider.close();
        return pulse;
    }

    public List<Pulse> getPulses(){
        TypedQuery<Pulse> query = sessionProvider.getSession().createQuery("FROM pulses",  Pulse.class);
        List<Pulse> results = query.getResultList();
        sessionProvider.close();
        return results;
    }
}
