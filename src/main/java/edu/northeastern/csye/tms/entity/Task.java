package edu.northeastern.csye.tms.entity;

import edu.northeastern.csye.tms.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 This class represents a Task entity, which stores information about a task that a user is assigned to.
 @author Goutham K
 */
@Entity
@Table(name = "tasks")
@NoArgsConstructor
@Getter @Setter
public class Task implements Marker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private int id;

    private String description;

    private String name;

    @Enumerated(EnumType.STRING)
    private Status status;

    private int points;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Pulse> pulses;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JoinColumn(name = "owner")
    private User user;

    public Task(String description, String name, Status status, int points) {
        this.description = description;
        this.name = name;
        this.status = status;
        this.points = points;
    }

    public void addPulse(Pulse pulse){
        if (pulses == null)
            pulses = new ArrayList<>();

        pulses.add(pulse);
    }
}