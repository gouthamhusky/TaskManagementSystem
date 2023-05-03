package edu.northeastern.csye.tms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 This class represents a Role entity, which stores information about roles that a user can have, for RBAC.
 Values are USER, ADMIN
 @author Goutham K
 */
@Entity
@Table(name = "roles")
@NoArgsConstructor
@Getter @Setter
public class Role implements Marker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int id;

    @Column(name = "role_name")
    private String roleName;

    @ManyToMany(cascade = {
            CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public void addUser(User user){
        if (users == null)
            users = new ArrayList<>();

        users.add(user);
    }
}