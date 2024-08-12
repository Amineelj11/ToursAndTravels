package com.toursandtravel.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String comment;
    private int rating;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;
}
