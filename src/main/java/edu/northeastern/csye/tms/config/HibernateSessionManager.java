package edu.northeastern.csye.tms.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class HibernateSessionManager {

    private static final ThreadLocal<Session> sessionThread = new ThreadLocal<>();
    private final SessionFactory sessionFactory;

    @Autowired
    public HibernateSessionManager(EntityManagerFactory entityManagerFactory) {
        Objects.requireNonNull(entityManagerFactory, "entityManagerFactory must not be null");
        this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
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