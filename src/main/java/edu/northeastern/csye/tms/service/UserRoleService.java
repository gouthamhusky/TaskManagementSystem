package edu.northeastern.csye.tms.service;

import edu.northeastern.csye.tms.dao.RoleDAO;
import edu.northeastern.csye.tms.dao.UserDAO;
import edu.northeastern.csye.tms.entity.Role;
import edu.northeastern.csye.tms.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 This class represents a service that handles User and Role objects and their respective persistenc
 @author Goutham K
 */
@Service
public class UserRoleService {

    private final UserDAO userDAO;

    public final RoleDAO roleDAO;

    @Autowired
    public UserRoleService(UserDAO userDAO, RoleDAO roleDAO) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
    }

    /**
     Persists a Role object.
     @param role the Role to be persisted
     */
    public void persistRole(Role role) {
        roleDAO.persist(role);
    }

    /**
     Updates a Role object.
     @param role the Role to be updated
     */
    public void updateRole(Role role) {
        roleDAO.update(role);
    }

    /**
     Deletes a Role object with the given id.
     @param id the id of the Role to be deleted
     */
    public void deleteRole(Integer id) {
        roleDAO.delete(id);
    }

    /**
     Retrieves a list of all Role objects.
     @return a list of all Role objects
     */
    public List<Role> getRoles() {
        return roleDAO.getRoles();
    }

    /**
     Retrieves a Role object with the given id.
     @param roleId the id of the Role to be retrieved
     @return the Role object with the given id
     */
    public Role getRole(Integer roleId) {
        return roleDAO.get(roleId);
    }

    /**
     Persists a User object.
     @param user the User to be persisted
     */
    public void persistUser(User user) {
        userDAO.persist(user);
    }

    /**
     Updates a User object.
     @param user the User to be updated
     */
    public void updateUser(User user) {
        userDAO.update(user);
    }

    /**
     Deletes a User object with the given id.
     @param id the id of the User to be deleted
     */
    public void deleteUser(Integer id) {
        userDAO.delete(id);
    }

    /**
     Retrieves a list of all User objects.
     @return a list of all User objects
     */
    public List<User> getUsers() {
        return userDAO.getUsers();
    }

    /**
     Retrieves a User object with the given id.
     @param userId the id of the User to be retrieved
     @return the User object with the given id
     */
    public User getUser(Integer userId) {
        return userDAO.get(userId);
    }

    /**
     Retrieves a User object with the given username.
     @param userName the username of the User to be retrieved
     @return the User object with the given username
     */
    public User getUser(String userName) {
        return userDAO.get(userName);
    }

    /**
     Retrieves the number of User objects.
     @return the number of User objects
     */
    public int getUserCount(){
        return userDAO.getUserCount();
    }
}