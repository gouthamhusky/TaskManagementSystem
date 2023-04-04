package edu.northeastern.csye.tms.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@Getter @Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private int id;

    private String description;

    private String name;

    private String status;

    private int points;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Pulse> pulses;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JoinColumn(name = "owner")
    private User user;

    public Task(String description, String name, String status, int points) {
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