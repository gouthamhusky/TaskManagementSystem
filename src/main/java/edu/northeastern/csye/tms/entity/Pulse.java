package edu.northeastern.csye.tms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pulses")
@NoArgsConstructor
@Getter @Setter
public class Pulse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pulse_id")
    private int id;

    private String comment;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JoinColumn(name = "task_id")
    private Task task;

    public Pulse(String comment, Task task) {
        this.comment = comment;
        this.task = task;
    }
}