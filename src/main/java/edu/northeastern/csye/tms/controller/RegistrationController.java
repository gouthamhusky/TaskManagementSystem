package edu.northeastern.csye.tms.controller;

import edu.northeastern.csye.tms.entity.Role;
import edu.northeastern.csye.tms.entity.User;
import edu.northeastern.csye.tms.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api")
public class RegistrationController {

    private final UserRoleService userRoleService;

    @Autowired
    public RegistrationController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @GetMapping("/register")
    public String registerUser(Model model){

        User user = new User();
        model.addAttribute("user", user);
        user.setEnabled(1);

        List<Role> roles = userRoleService.getRoles();
        model.addAttribute("allRoles", roles);

        return "users/register";
    }
}