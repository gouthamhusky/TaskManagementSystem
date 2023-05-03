package edu.northeastern.csye.tms.dto;

import edu.northeastern.csye.tms.entity.Pulse;
import edu.northeastern.csye.tms.entity.Role;
import edu.northeastern.csye.tms.entity.Task;
import edu.northeastern.csye.tms.entity.User;
import java.util.List;
import lombok.Data;

/**
 A data transfer object representing a User object along with related Role, Task, and Pulse objects,
 as well as an isAdmin flag.
 @author Goutham K
 */
@Data
public class UserDTO {

    private User user;
    private List<Role> roles;
    private List<Task> tasks;
    private List<Pulse> pulses;
    private boolean isAdmin;
}
