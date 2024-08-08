package com.example.tms.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String status;
    private String priority;

    @ManyToOne
    private User author;

    @ManyToOne
    private User assignee;

    @OneToMany(mappedBy = "task")
    private Set<Comment> comments;

}

