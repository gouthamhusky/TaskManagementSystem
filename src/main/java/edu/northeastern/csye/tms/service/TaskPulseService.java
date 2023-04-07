package edu.northeastern.csye.tms.service;

import edu.northeastern.csye.tms.dao.PulseDAO;
import edu.northeastern.csye.tms.dao.TaskDAO;
import edu.northeastern.csye.tms.entity.Pulse;
import edu.northeastern.csye.tms.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskPulseService {

    private final TaskDAO taskDAO;
    private final PulseDAO pulseDAO;

    @Autowired
    public TaskPulseService(TaskDAO taskDAO, PulseDAO pulseDAO) {
        this.taskDAO = taskDAO;
        this.pulseDAO = pulseDAO;
    }

    public void persistTask(Task task) {
        taskDAO.persist(task);
    }

    public void updateTask(Task task) {
        taskDAO.update(task);
    }

    public void deleteTask(Integer id) {
        taskDAO.delete(id);
    }

    public List<Task> getTasks() {
        return taskDAO.getTasks();
    }

    public Task getTask(Integer taskId) {
        return taskDAO.get(taskId);
    }

    public void persistPulse(Pulse pulse) {
        pulseDAO.persist(pulse);
    }

    public void updatePulse(Pulse pulse) {
        pulseDAO.update(pulse);
    }

    public void deletePulse(Integer id) {
        pulseDAO.delete(id);
    }

    public List<Pulse> getPulses() {
        return pulseDAO.getPulses();
    }

    public Pulse getPulse(Integer pulseId) {
        return pulseDAO.get(pulseId);
    }
}