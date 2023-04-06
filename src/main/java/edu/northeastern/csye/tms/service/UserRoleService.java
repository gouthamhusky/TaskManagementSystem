package edu.northeastern.csye.tms.service;

import edu.northeastern.csye.tms.dao.RoleDAO;
import edu.northeastern.csye.tms.dao.UserDAO;
import edu.northeastern.csye.tms.entity.Role;
import edu.northeastern.csye.tms.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

    private final UserDAO userDAO;
    private final RoleDAO roleDAO;

    @Autowired
    public UserRoleService(UserDAO userDAO, RoleDAO roleDAO) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
    }

    public void persistRole(Role role) {
        roleDAO.persist(role);
    }

    public void updateRole(Role role) {
        roleDAO.update(role);
    }

    public void deleteRole(Role role) {
        roleDAO.delete(role);
    }

    public List<Role> getRoles() {
        return roleDAO.getRoles();
    }

    public Role getRole(Integer roleId) {
        return roleDAO.get(roleId);
    }

    public void persistUser(User user) {
        userDAO.persist(user);
    }

    public void updateUser(User user) {
        userDAO.update(user);
    }

    public void deleteUser(User user) {
        userDAO.delete(user);
    }

    public List<User> getUsers() {
        return userDAO.getUsers();
    }

    public User getUser(Integer userId) {
        return userDAO.get(userId);
    }
}