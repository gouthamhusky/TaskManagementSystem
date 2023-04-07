package edu.northeastern.csye.tms.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class HibernateSessionManager {

    private static final ThreadLocal<Session> sessionThread = new ThreadLocal<>();
    private final SessionFactory sessionFactory;

    private final EntityManager entityManager;
    
    @Autowired
    public HibernateSessionManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        Objects.requireNonNull(entityManager, "entityManager must not be null");
        this.sessionFactory = entityManager.getEntityManagerFactory().unwrap(SessionFactory.class);
    }

    public Session getSession() {
        Session session = sessionThread.get();

        if (session == null) {
            session = sessionFactory.openSession();
            sessionThread.set(session);
        }
        return session;
    }

    public void close() {
//        getSession().close();
        sessionThread.set(null);
    }
}