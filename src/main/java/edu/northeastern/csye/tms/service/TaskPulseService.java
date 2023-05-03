package edu.northeastern.csye.tms.service;

import edu.northeastern.csye.tms.dao.PulseDAO;
import edu.northeastern.csye.tms.dao.TaskDAO;
import edu.northeastern.csye.tms.entity.Pulse;
import edu.northeastern.csye.tms.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
Service layer class for performing CRUD operations on Task and Pulse entities
 @author Goutham K
 */
@Service
public class TaskPulseService {

    private final TaskDAO taskDAO;
    private final PulseDAO pulseDAO;

    @Autowired
    public TaskPulseService(TaskDAO taskDAO, PulseDAO pulseDAO) {
        this.taskDAO = taskDAO;
        this.pulseDAO = pulseDAO;
    }

    /**
     Persists the given Task entity.
     @param task the Task to persist
     */
    public void persistTask(Task task) {
        taskDAO.persist(task);
    }

    /**
     Updates the given Task entity.
     @param task the Task to update
     */
    public void updateTask(Task task) {
        taskDAO.update(task);
    }

    /**
     Deletes the Task entity with the given ID.
     @param id the ID of the Task to delete
     */
    public void deleteTask(Integer id) {
        taskDAO.delete(id);
    }

    /**
     Retrieves a list of all Task entities.
     @return a list of all Task entities
     */
    public List<Task> getTasks() {
        return taskDAO.getTasks();
    }

    /**
     Retrieves the Task entity with the given ID.
     @param taskId the ID of the Task to retrieve
     @return the Task entity with the given ID
     */
    public Task getTask(Integer taskId) {
        return taskDAO.get(taskId);
    }

    /**
     Retrieves the count of all Task entities.
     @return the count of all Task entities
     */
    public int getTaskCount(){
        return taskDAO.getTaskCount();
    }

    /**
     Persists the given Pulse entity.
     @param pulse the Pulse to persist
     */
    public void persistPulse(Pulse pulse) {
        pulseDAO.persist(pulse);
    }

    /**
     Updates the given Pulse entity.
     @param pulse the Pulse to update
     */
    public void updatePulse(Pulse pulse) {
        pulseDAO.update(pulse);
    }

    /**
     Deletes the Pulse entity with the given ID.
     @param id the ID of the Pulse to delete
     */
    public void deletePulse(Integer id) {
        pulseDAO.delete(id);
    }

    /**
     Retrieves a list of all Pulse entities.
     @return a list of all Pulse entities
     */
    public List<Pulse> getPulses() {
        return pulseDAO.getPulses();
    }

    /**
     Retrieves the Pulse entity with the given ID.
     @param pulseId the ID of the Pulse to retrieve
     @return the Pulse entity with the given ID
     */
    public Pulse getPulse(Integer pulseId) {
        return pulseDAO.get(pulseId);
    }

    /**
     Retrieves the count of all Pulse entities.
     @return the count of all Pulse entities
     */
    public int getPulseCount(){
        return pulseDAO.getPulseCount();
    }
}