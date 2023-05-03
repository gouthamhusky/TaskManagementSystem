package edu.northeastern.csye.tms.utils;

import edu.northeastern.csye.tms.entity.Pulse;
import edu.northeastern.csye.tms.entity.Role;
import edu.northeastern.csye.tms.entity.Task;
import edu.northeastern.csye.tms.service.TaskPulseService;
import edu.northeastern.csye.tms.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ControllerUtils {

    private final UserRoleService userRoleService;
    private final TaskPulseService taskPulseService;

    @Autowired
    public ControllerUtils(UserRoleService userRoleService, TaskPulseService taskPulseService) {
        this.userRoleService = userRoleService;
        this.taskPulseService = taskPulseService;
    }

    public List<Pulse> getPulsesById(String[] ids){
        List<Pulse> pulses = new ArrayList<>();
        for (String id: ids){
            pulses.add(taskPulseService.getPulse(Integer.parseInt(id)));
        }

        return pulses;
    }

    public List<Task> getTasksById(String[] ids){
        List<Task> tasks = new ArrayList<>();
        for (String id: ids){
            tasks.add(taskPulseService.getTask(Integer.parseInt(id)));
        }

        return tasks;
    }

    public List<Role> getRolesById(String[] ids){
        List<Role> roles = new ArrayList<>();
        for (String id: ids){
            roles.add(userRoleService.getRole(Integer.parseInt(id)));
        }

        return roles;
    }
}
