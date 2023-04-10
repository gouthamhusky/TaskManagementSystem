package edu.northeastern.csye.tms.controller;

import edu.northeastern.csye.tms.entity.User;
import edu.northeastern.csye.tms.service.TaskPulseService;
import edu.northeastern.csye.tms.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TaskMgmtController {

   private final UserRoleService userRoleService;

   private final TaskPulseService taskPulseService;

    @Autowired
    public TaskMgmtController(UserRoleService userRoleService, TaskPulseService taskPulseService) {
        this.userRoleService = userRoleService;
        this.taskPulseService = taskPulseService;
    }

    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute("user")User user){

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRoleService.persistUser(user);

        return "redirect:/login";
    }
}