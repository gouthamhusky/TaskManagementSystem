package edu.northeastern.csye.tms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TaskMgmtController {

//   TaskMgmtController private TaskService taskService;
//
//    @Autowired
//    public TaskMgmtController(TaskService taskService) {
//        this.taskService = taskService;
//    }
//
//    @GetMapping("/landingPage")
//    public String landingPage(Model model){
//
//        // main app view where the user can add tasks, view users and other operations based on access level
//        return "landing-page";
//    }
//
//    @GetMapping("/login")
//    public String login(Model model){
//
//        //
//        return "login-view";
//    }
//
//    @GetMapping("/createTask")
//    public String createTask(Model model){
//
//        //
//        return "redirect:/task-view?taskID=taskID";
//    }
//
//    @PostMapping("/updateTask")
//    public String updateTask(Model model){
//
//        // update a given task by ID
//        return "tasks-list";
//    }
//
//    @PostMapping("/addTasks")
//    public String addTasks(Model model){
//
//        // add multiple tasks in the UI. This gets persisted to the DB
//        return "tasks-list";
//    }
//
//    @PostMapping("/editTasks")
//    public String editTasks(Model model){
//
//        // view the list of tasks and allows user to edit any of them
//        return "tasks-list";
//    }
//
//    @PostMapping("/addPulseToTask")
//    public String addPulseToTask(Model model){
//
//        // adds a comment to a given task by ID
//        return "redirect:/task-view?taskID=taskID";
//    }
//
//    @GetMapping("/getUsers")
//    public String getUsers(Model model){
//
//        // makes a call to the DB to fetch all users
//        return "users-list";
//    }
//
//    @GetMapping("/getTasks")
//    public String getTasks(Model model){
//
//        // gets all the tasks from the DB and displays them as a list
//        return "tasks-list";
//    }
//
//    @GetMapping("/deleteTask")
//    public String deleteTask(Model model){
//
//        // deletes the task by ID. Makes a call to DB
//        return "tasks-list";
//    }
//
//    @GetMapping("/displayTask")
//    public String displayTask(Model model){
//
//        // view mode of the task. Allows user to view and add pulse comments
//        return "display-task";
//    }
}
