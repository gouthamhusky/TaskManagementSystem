package edu.northeastern.csye.tms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 This class represents a User entity, which stores information about details like username, password etc.
 Passwords are encoded using the Bcrypt algorithm and store in a MySQL DB
 @author Goutham K
 */
@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter @Setter
public class User implements Marker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "firstname")
    @NotEmpty
    private String firstName;

    @Column(name = "lastname")
    @NotEmpty
    private String lastName;

    @Column(name = "username")
    @NotEmpty
    private String userName;

    @NotEmpty
    private String password;

    private int enabled;

    @OneToMany(mappedBy = "user",
               cascade = CascadeType.ALL,
               fetch = FetchType.EAGER
    )
    private List<Task> tasks;

    @ManyToMany(cascade = {
            CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH
    },      fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    public User(String firstName, String lastName, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.enabled = 1;
    }

    public void addRole(Role role){
        if (roles == null)
            roles = new ArrayList<>();

        roles.add(role);
    }

    public void addTask(Task task){
        if (tasks == null)
            tasks = new ArrayList<>();

        tasks.add(task);
    }
}