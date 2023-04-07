package edu.northeastern.csye.tms.persistence;

import edu.northeastern.csye.tms.entity.Role;
import edu.northeastern.csye.tms.entity.User;
import edu.northeastern.csye.tms.service.UserRoleService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserandRoleTest {

    @Autowired
    private UserRoleService userRoleService;

    private static int testerRoleId;
    private static int managerId;

    private static int testUserId;

    @BeforeAll
    public static void setup(@Autowired UserRoleService userRoleService){

        // create a new role called ROLE_TESTER
        Role role = new Role("ROLE_TESTER");

        userRoleService.persistRole(role);
        testerRoleId = role.getId();
    }

    @Test
    @Order(1)
    public void persistRole(){
        Role role = new Role("ROLE_MANAGER");

        userRoleService.persistRole(role);
        managerId = role.getId();

        Assertions.assertEquals(role.getRoleName(), userRoleService.getRole(managerId).getRoleName());
    }

    @Test
    @Order(2)
    public void getRoles(){
        List<Role> roles = userRoleService.getRoles();
        List<String> roleNames = new ArrayList<>();
        roles.forEach(role -> roleNames.add(role.getRoleName()));

        Assertions.assertArrayEquals(new String[]{"ROLE_USER", "ROLE_ADMIN", "ROLE_TESTER", "ROLE_MANAGER"}, roleNames.toArray());
    }

    @Test
    @Order(3)
    public void getRole(){
        Role admin = userRoleService.getRole(2);

        Assertions.assertEquals("ROLE_ADMIN", admin.getRoleName());
    }

    @Test
    @Order(4)
    public void updateRole(){
        Role tester = userRoleService.getRole(testerRoleId);
        tester.setRoleName("ROLE_TEST");
        userRoleService.updateRole(tester);

        tester = userRoleService.getRole(testerRoleId);

        Assertions.assertEquals("ROLE_TEST", tester.getRoleName());
    }

    @Test
    @Order(5)
    public void deleteRole(){
        userRoleService.deleteRole(testerRoleId);
        userRoleService.deleteRole(managerId);

        Assertions.assertEquals(2, userRoleService.getRoles().size());
    }

    @Test
    @Order(6)
    public void persistUser(){
        User user = new User("Yusuf", "Ozbek", "y.ozbek", "neu.edu");
        userRoleService.persistUser(user);
        testUserId = user.getId();

        Assertions.assertEquals(user.getFirstName(), userRoleService.getUser(testUserId).getFirstName());
    }

    @Test
    @Order(7)
    public void updateUser(){

        User user = userRoleService.getUser(testUserId);
        user.setUserName("yusuf.neu");
        userRoleService.updateUser(user);

        Assertions.assertEquals(user.getUserName(), userRoleService.getUser(testUserId).getUserName());
    }

    @Test
    @Order(8)
    public void getUsers(){

        List<User> users = userRoleService.getUsers();
        List<String> userNames = new ArrayList<>();
        users.forEach(user -> {
            userNames.add(user.getUserName());
        });

        Assertions.assertArrayEquals(new String[]{"jdoe", "jane.doe", "asmith", "bjohnson", "elee", "yusuf.neu"}, userNames.toArray());
    }

    @Test
    @Order(9)
    public void deleteUser(){

        userRoleService.deleteUser(testUserId);

        Assertions.assertEquals(5, userRoleService.getUsers().size());
    }

    @Test
    @AfterAll
    public static void cleanupCheck(@Autowired UserRoleService userRoleService){
        List<Role> roles = userRoleService.getRoles();
        List<String> roleNames = new ArrayList<>();
        roles.forEach(role -> roleNames.add(role.getRoleName()));

        Assertions.assertArrayEquals(new String[]{"ROLE_USER", "ROLE_ADMIN"}, roleNames.toArray());
    }
}