package edu.northeastern.csye.tms.controller;

import edu.northeastern.csye.tms.entity.Pulse;
import edu.northeastern.csye.tms.entity.Task;
import edu.northeastern.csye.tms.service.TaskPulseService;
import io.micrometer.core.annotation.Timed;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 The PulseController class handles HTTP requests related to Pulse entity
 @author Goutham K
 */
@Controller
@Log4j2
public class PulseController {

    private final TaskPulseService taskPulseService;

    @Autowired
    public PulseController(TaskPulseService taskPulseService) {
        this.taskPulseService = taskPulseService;
    }

    /**
     Updates a pulse record in the database.
     @param pulse the Pulse object to be updated
     @param request the HttpServletRequest object for the current request
     @return the name of the redirect view to display after the update is complete
     */
    @Timed(value = "tms.pulse.update")
    @PostMapping("/pulses/update")
    public String updaterPulse(@ModelAttribute("pulse") Pulse pulse, HttpServletRequest request, @RequestParam("id") int id){
        Task task = taskPulseService.getTask(id);
        pulse.setTask(task);

        taskPulseService.updatePulse(pulse);

        log.info("Rendering the update pulse page");
        return "redirect:/tasks/view";
    }

    /**
     Renders the view for adding a new Pulse object to a specified Task.
     @param model the Model object to be used for rendering the view
     @param taskId the ID of the Task object to which the new Pulse object will be associated
     @return the name of the view to be rendered
     */
    @GetMapping("/pulses/add/view")
    public String addPulseView(Model model, @RequestParam("taskId") int taskId){
        Pulse pulse = new Pulse();
        model.addAttribute("newPulse", pulse);
        model.addAttribute("taskId", taskId);

        log.info("Rendering the add pulse page");
        return "add-pulse";
    }


    /**
     Adds a new Pulse object to the database.
     @param newPulse the Pulse object to be added
     @param taskId the ID of the Task object to which the new Pulse object will be associated
     @return the name of the redirect view to display after the add is complete
     */
    @Timed(value = "tms.pulse.add")
    @PostMapping("/pulses/add")
    public String addPulse(@ModelAttribute("newPulse") Pulse newPulse, @RequestParam("taskId") int taskId){
        newPulse.setTask(taskPulseService.getTask(taskId));
        taskPulseService.persistPulse(newPulse);

        log.info("Rendering the add pulse page");
        return "redirect:/home";
    }

    /**
     Renders the view for updating an existing Pulse object.
     @param id the ID of the Pulse object to be updated
     @param model the Model object to be used for rendering the view
     @return the name of the view to be rendered
     */
    @GetMapping("/pulses/update/view")
    public String updatePulseView(@RequestParam("id") int id, Model model){
        Pulse pulse  = taskPulseService.getPulse(id);
        model.addAttribute("pulse", pulse);

        log.info("Rendering update-pulse view for pulse with ID {}", pulse.getId());
        return "update-pulse";
    }

    @GetMapping("/pulses/delete")
    public String deletePulse(@RequestParam("id") int id){

        taskPulseService.deletePulse(id);

        return "redirect:/home";
    }

}
