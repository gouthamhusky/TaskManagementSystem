package edu.northeastern.csye.tms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 This class represents a Pulse entity, which stores information about a particular comment added to a Task.
 @author Goutham K
 */
@Entity
@Table(name = "pulses")
@NoArgsConstructor
@Getter @Setter
public class Pulse implements Marker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pulse_id")
    private int id;

    private String comment;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JoinColumn(name = "task_id")
    private Task task;

    public Pulse(String comment) {
        this.comment = comment;
    }
}