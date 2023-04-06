package edu.northeastern.csye.tms.dao;

import edu.northeastern.csye.tms.config.HibernateSessionManager;
import edu.northeastern.csye.tms.entity.Task;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskDAO implements GenericDAO<Task>{

    private final HibernateSessionManager sessionManager;

    @Autowired
    public TaskDAO(HibernateSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    @Transactional
    public void persist(Task task) {
        sessionManager.getSession().persist(task);
        sessionManager.close();
    }

    @Override
    @Transactional
    public void update(Task task) {
        sessionManager.getSession().merge(task);
        sessionManager.close();
    }

    @Override
    @Transactional
    public void delete(Task task) {
        sessionManager.getSession().remove(task);
        sessionManager.close();
    }

    @Override
    public Task get(Integer id) {
        Task task = sessionManager.getSession().get(Task.class, id);
        sessionManager.close();
        return task;
    }

    public List<Task> getTasks(){
        TypedQuery<Task> query = sessionManager.getSession().createQuery("FROM tasks", Task.class);
        List<Task> tasks = query.getResultList();
        sessionManager.close();
        return tasks;
    }
}
