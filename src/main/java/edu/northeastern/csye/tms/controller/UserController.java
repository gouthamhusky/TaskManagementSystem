package edu.northeastern.csye.tms.controller;

import edu.northeastern.csye.tms.dto.UserDTO;
import edu.northeastern.csye.tms.entity.Pulse;
import edu.northeastern.csye.tms.entity.User;
import edu.northeastern.csye.tms.service.TaskPulseService;
import edu.northeastern.csye.tms.service.UserRoleService;
import edu.northeastern.csye.tms.utils.ControllerUtils;
import io.micrometer.core.annotation.Timed;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**

 This controller class handles user-related operations such as creating, updating and deleting users.
 It is responsible for rendering user-related views and processing HTTP requests related to users.
 @author Goutham K
 */
@Controller
@Log4j2
public class UserController {

    private final UserRoleService userRoleService;
    private final TaskPulseService taskPulseService;
    private final ControllerUtils controllerUtils;

    private final HttpSession httpSession;

    @Autowired
    public UserController(UserRoleService userRoleService, TaskPulseService taskPulseService, ControllerUtils controllerUtils, HttpSession httpSession) {
        this.userRoleService = userRoleService;
        this.taskPulseService = taskPulseService;
        this.controllerUtils = controllerUtils;
        this.httpSession = httpSession;
    }

    /**

     This method handles HTTP POST requests to the "/users/save" URL and creates a new user object with
     the data submitted in the request. The user object is then saved using the userRoleService object.
     The method also logs the creation of the new user.
     @param user The user object submitted in the request
     @return A string representing the URL to redirect to after the user is created
     */
    @Timed(value = "tms.user.save")
    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute("user") User user){
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRoleService.persistUser(user);

        log.info("Created new user with username {} successfully", user.getUserName());
        return "redirect:/login";
    }

    /**

     This method handles HTTP GET requests to the "/users/update/view" URL and renders the view
     for updating a user's profile. It retrieves the current user's information from the HttpSession
     object and adds it to the model to be rendered by the view.
     @param id The ID of the user to be updated
     @param model The Model object to be rendered by the view
     @return A string representing the name of the view to be rendered
     */
    @GetMapping("/users/update/view")
    public String updateUserView(@RequestParam("id") int id, Model model){
        UserDTO currentUserDTO = (UserDTO) httpSession.getAttribute("currentUserDTO");
        model.addAttribute("currentUser", currentUserDTO.getUser());

        log.info("Rendering the update-user view for user with ID {}", currentUserDTO.getUser().getUserName());
        return "update-profile";
    }

    /**
     This method handles HTTP POST requests to the "/users/update" URL and updates a user's profile with
     the data submitted in the request. The method retrieves the current user's information from the
     HttpSession object and updates the user's password, tasks and roles using the submitted data
     @param request The HttpServletRequest object containing the submitted data
     @return A string representing the URL to redirect to after the user is updated
     */
    @Timed(value = "tms.user.update")
    @PostMapping("/users/update")
    public String updaterUser(HttpServletRequest request){
        String[] taskIds = request.getParameterValues("taskIds");
        String[] roleIds = request.getParameterValues("roleIds");

        UserDTO currentUserDTO = (UserDTO) httpSession.getAttribute("currentUserDTO");
        User user = currentUserDTO.getUser();

        user.setPassword("{bcrypt}" + new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setTasks(controllerUtils.getTasksById(taskIds));
        user.setRoles(controllerUtils.getRolesById(roleIds));
        userRoleService.updateUser(user);

        log.info("Update user with ID {}, redirecting to home page", user.getId());
        return "redirect:/home";
    }

}