package edu.northeastern.csye.tms.dao;

import edu.northeastern.csye.tms.entity.Task;
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

    @Override
    @Transactional
    public void persist(Task task) {
        entityManager.persist(task);
    }

    @Override
    @Transactional
    public void update(Task task) {
        entityManager.merge(task);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Task task = entityManager.find(Task.class, id);
        entityManager.remove(task);
    }

    @Override
    @Transactional
    public Task get(Integer id) {
        return entityManager.find(Task.class, id);
    }

    public List<Task> getTasks(){
        TypedQuery<Task> query = entityManager.createQuery("FROM Task", Task.class);
        List<Task> tasks = query.getResultList();
        return tasks;
    }
}