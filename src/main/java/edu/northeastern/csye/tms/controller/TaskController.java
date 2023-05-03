package edu.northeastern.csye.tms.controller;

import edu.northeastern.csye.tms.dto.UserDTO;
import edu.northeastern.csye.tms.entity.Task;
import edu.northeastern.csye.tms.entity.User;
import edu.northeastern.csye.tms.service.TaskPulseService;
import edu.northeastern.csye.tms.service.UserRoleService;
import edu.northeastern.csye.tms.utils.ControllerUtils;
import io.micrometer.core.annotation.Timed;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 A controller class that handles requests related to tasks.
 This class handles requests related to tasks, such as adding, updating, and deleting tasks. It uses
 a {@link UserRoleService} to retrieve user roles, a {@link TaskPulseService} to persist and update
 task and pulse data, a {@link ControllerUtils} to retrieve pulses by ID, and an {@link HttpSession}
 to manage user sessions.
 @author Goutham K
 */
@Controller
@Log4j2
public class TaskController {

    private final UserRoleService userRoleService;
    private final TaskPulseService taskPulseService;
    private final ControllerUtils controllerUtils;

    private final HttpSession httpSession;

    @Autowired
    public TaskController(UserRoleService userRoleService, TaskPulseService taskPulseService, ControllerUtils controllerUtils, HttpSession httpSession) {
        this.userRoleService = userRoleService;
        this.taskPulseService = taskPulseService;
        this.controllerUtils = controllerUtils;
        this.httpSession = httpSession;
    }

    /**
     Retrieves all tasks.
     This method retrieves all tasks and renders the "all-tasks" view.
     @return a String representing the name of the "all-tasks" view
     */
    @GetMapping("/tasks/view")
    public String allTasks(){
        String userName = ((UserDTO) httpSession.getAttribute("currentUserDTO")).getUser().getUserName();
        log.info("Rendering view for all tasks for user {}", userName);

        return "all-tasks";
    }

    /**
     Retrieves all tasks for the current user.
     This method retrieves all tasks for the current user and renders the "user-tasks" view.
     @return a String representing the name of the "user-tasks" view
     */
    @GetMapping("/tasks/user/view")
    public String allUserTasks(){
        String userName = ((UserDTO) httpSession.getAttribute("currentUserDTO")).getUser().getUserName();
        log.info("Rendering specific tasks view for user {}",  userName);

        return "user-tasks";
    }

    /**
     Retrieves the update-task view.
     This method retrieves the update-task view for a specified task and renders it. It also retrieves
     all users with the "ROLE_ADMIN" role if the current user has that role.
     @param id an int representing the ID of the task to update
     @param model a Model instance used to add attributes to the view
     @param authentication an Authentication instance representing the current user's authentication
     @return a String representing the name of the "update-task" view
     */
    @GetMapping("/tasks/update/view")
    public String updateTaskView(@RequestParam("id") int id, Model model, Authentication authentication){

        UserDTO adminDTO = (UserDTO) httpSession.getAttribute("adminDTO");
        List<Task> tasks = adminDTO.getTasks();
        tasks.forEach(task -> {
            if (task.getId() == id){
                model.addAttribute("task", task);
                log.info("Rendering update-task view for task {}", task.getId());
            }
        });

        User user = userRoleService.getUser(authentication.getName());
        user.getRoles().forEach(role -> {
            if (role.getRoleName().equals("ROLE_ADMIN")){
                model.addAttribute("users", userRoleService.getUsers());
            }
        });

        return "update-task";
    }


    /**
     * Displays the view for adding a new task.
     * @param model an object that holds the model attributes for the view
     * @param authentication the authenticated user making the request
     * @return a string that represents the name of the view to render
     */
    @GetMapping("/tasks/add/view")
    public String addTaskView(Model model, Authentication authentication){
        Task newTask = new Task();
        model.addAttribute("newTask", newTask);

        User user = userRoleService.getUser(authentication.getName());
        user.getRoles().forEach(role -> {
            if (role.getRoleName().equals("ROLE_ADMIN")){
                model.addAttribute("users", userRoleService.getUsers());
            }
        });

        log.info("Rendering add-task view for new task");
        return "add-task";
    }

    /**
     Add a new task
     @param newTask the Task object to add
     @param request the HttpServletRequest object for the request
     @return the view name to redirect to after adding the task
     */
    @Timed(value = "tms.task.add")
    @PostMapping("/tasks/add")
    public String addTask(@ModelAttribute("newTask")Task newTask, HttpServletRequest request){

        int ownerID = Integer.parseInt(request.getParameter("userid"));
        User owner = userRoleService.getUser(ownerID);

        newTask.setUser(owner);
        taskPulseService.persistTask(newTask);
        log.info("Added new task ID {} for User {}, redirecting to /home", newTask.getId(), ownerID);

        return "redirect:/home";
    }

    /**
     Updates a task with the provided details.
     @param task the updated Task object
     @param request the HttpServletRequest object containing the user ID and pulse IDs
     @return a String representing the name of the view to redirect to after the update is complete
     */
    @Timed(value = "tms.task.update")
    @PostMapping("/tasks/update")
    public String updateTask(@ModelAttribute Task task, HttpServletRequest request){
        Integer userid = Integer.parseInt(request.getParameter("userid"));
        String[] pulseIds = request.getParameterValues("pulseIds");

        User user = userRoleService.getUser(userid);
        task.setUser(user);
        if (pulseIds != null)
            task.setPulses(controllerUtils.getPulsesById(pulseIds));
        taskPulseService.updateTask(task);

        log.info("Updated task with ID {} for User {}, redirecting to all-tasks view", task.getId(), userid);
        return "redirect:/tasks/view";
    }

    /**
     Deletes a task with the given ID from the system.
     @param id the ID of the task to be deleted
     @param model the model used to pass data to the view
     @return the name of the view to be rendered after the task is deleted
     */
    @Timed(value = "tms.task.delete")
    @GetMapping("/tasks/delete")
    public String deleteTask(@RequestParam("id") int id, Model model){
        taskPulseService.deleteTask(id);

        log.info("Deleted task ID {}, redirecting to all-tasks view", id);
        return "redirect:/tasks/view";
    }
}
