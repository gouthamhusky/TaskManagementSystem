package edu.northeastern.csye.tms.dao;

import edu.northeastern.csye.tms.config.HibernateSessionProvider;
import edu.northeastern.csye.tms.entity.Task;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskDAO implements GenericDAO<Task>{

    private HibernateSessionProvider sessionProvider;

    @Autowired
    public TaskDAO(HibernateSessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

    @Override
    public void save(Task task) {
        sessionProvider.getSession().persist(task);
        sessionProvider.close();
    }

    @Override
    public void update(Task task) {
        sessionProvider.getSession().merge(task);
        sessionProvider.close();
    }

    @Override
    public void delete(Task task) {
        sessionProvider.getSession().remove(task);
        sessionProvider.close();
    }

    @Override
    public Task getById(Integer id) {
        Task task = sessionProvider.getSession().get(Task.class, id);
        sessionProvider.close();
        return task;
    }

    public List<Task> getTasks(){
        TypedQuery<Task> query = sessionProvider.getSession().createQuery("FROM tasks", Task.class);
        List<Task> tasks = query.getResultList();
        sessionProvider.close();
        return tasks;
    }
}
