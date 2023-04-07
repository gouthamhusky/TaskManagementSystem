package edu.northeastern.csye.tms.dao;

import edu.northeastern.csye.tms.entity.Task;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskDAO implements GenericDAO<Task>{

    @PersistenceContext
    private EntityManager entityManager;

    private ThreadLocal<EntityManager> threadLocalEntityManager = new ThreadLocal<>();

    @Override
    @Transactional
    public void persist(Task task) {
        getEntityManager().persist(task);
    }

    @Override
    @Transactional
    public void update(Task task) {
        getEntityManager().merge(task);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Task task = getEntityManager().find(Task.class, id);
        getEntityManager().remove(task);
    }

    @Override
    @Transactional
    public Task get(Integer id) {
        return getEntityManager().find(Task.class, id);
    }

    public List<Task> getTasks(){
        TypedQuery<Task> query = getEntityManager().createQuery("FROM Task", Task.class);
        List<Task> tasks = query.getResultList();
        return tasks;
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