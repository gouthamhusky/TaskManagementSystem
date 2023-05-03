package edu.northeastern.csye.tms.controller;

import edu.northeastern.csye.tms.dto.UserDTO;
import edu.northeastern.csye.tms.entity.Pulse;
import edu.northeastern.csye.tms.entity.Role;
import edu.northeastern.csye.tms.entity.Task;
import edu.northeastern.csye.tms.entity.User;
import edu.northeastern.csye.tms.service.TaskPulseService;
import edu.northeastern.csye.tms.service.UserRoleService;
import io.micrometer.core.annotation.Timed;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 This class is a Spring MVC controller responsible for handling user login and registration requests,
 and rendering the home page for authenticated users
 @author Goutham K
 */
@Controller
@Log4j2
public class LoginController {

    private final UserRoleService userRoleService;

    private final TaskPulseService taskPulseService;

    private final HttpSession httpSession;

    @Autowired
    public LoginController(UserRoleService userRoleService, TaskPulseService taskPulseService, HttpSession httpSession) {
        this.userRoleService = userRoleService;
        this.taskPulseService = taskPulseService;
        this.httpSession = httpSession;
    }

    /**
     Renders the login page when the user navigates to /login.
     @return The name of the view to render (login-page).
     */
    @Timed(value = "tms.login.time", description = "Time taken to serve login request",
            extraTags = {"#{authentication?.name}"})
    @GetMapping("/login")
    public String login(){
        log.info("Rendering login-page");

        return "login-page";
    }

    /**
     Renders the registration page when the user navigates to /users/register, and passes a new User object and a list
     of all roles to the view.
     @param model The Model object to add the User object and list of roles to.
     @return The name of the view to render (register).
     */
    @Timed(value = "tms.register.time", description = "Time taken to register new user",
            extraTags = {"#{authentication?.name}"})
    @GetMapping("/users/register")
    public String registerUser(Model model){
        log.info("Registering new user");
        User user = new User();
        user.setEnabled(1);
        model.addAttribute(user);

        List<Role> roles = userRoleService.getRoles();
        model.addAttribute("allRoles", roles);

        log.info("Rendering register-page");
        return "register";
    }


    /**
     Renders the home page when the user navigates to /home, and sets the appropriate attributes in the session
     based on the user's role.
     @param model The Model object to add the UserDTO object to.
     @param authentication The Authentication object containing the authenticated user's information.
     @return The name of the view to render (home).
     */
    @GetMapping("/home")
    public String homePage(Model model, Authentication authentication){

        List<Task> userTasks;
        List<Pulse> userPulses = new ArrayList<>();
        UserDTO userDTO = new UserDTO();
        UserDTO adminDTO = new UserDTO();
        List<Task> allTasks;
        List<Pulse> allPulses;
        User user = userRoleService.getUser(authentication.getName());


        if (user.getRoles().stream().anyMatch(role -> role.getRoleName().equals("ROLE_ADMIN"))){
            allTasks = taskPulseService.getTasks();
            allPulses = taskPulseService.getPulses();

            userDTO.setAdmin(true);

            adminDTO.setUser(user);
            adminDTO.setAdmin(true);
            adminDTO.setTasks(allTasks);
            adminDTO.setPulses(allPulses);
            adminDTO.setRoles(user.getRoles());
            httpSession.setAttribute("adminDTO", adminDTO);
            log.info("Admin user " + user.getUserName() + " logged in.");
        }
        else{
            userDTO.setAdmin(false);
            log.info("User " + user.getUserName() + " logged in.");
        }

        userTasks = user.getTasks();
        for (Task task: userTasks){
            userPulses.addAll(task.getPulses());
        }

        userDTO.setUser(user);
        userDTO.setTasks(userTasks);
        userDTO.setRoles(user.getRoles());
        userDTO.setPulses(userPulses);

        httpSession.setAttribute("currentUserDTO", userDTO);
        log.info("Rendering home page for user " + user.getUserName());
//        }else{
//            if (user.getRoles().stream().anyMatch(role -> role.getRoleName().equals("ROLE_ADMIN"))){
//                adminDTO.setTasks(taskPulseService.getTasks());
//                adminDTO.setPulses(taskPulseService.getPulses());
//                httpSession.setAttribute("adminDTO", adminDTO);
//            }
//            userTasks = user.getTasks();
//            userDTO.setTasks(userTasks);
//            userPulses = new ArrayList<>();
//            for (Task task: userTasks){
//                userPulses.addAll(task.getPulses());
//            }
//            userDTO.setPulses(userPulses);
//            httpSession.setAttribute("currentUserDTO", userDTO);
//        }

        return "home";
    }
}