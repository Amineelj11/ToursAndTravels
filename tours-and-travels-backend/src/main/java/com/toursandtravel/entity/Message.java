package com.toursandtravel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;

    private String text;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;



}
