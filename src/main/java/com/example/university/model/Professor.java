package com.example.university.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "professor")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String department;
}