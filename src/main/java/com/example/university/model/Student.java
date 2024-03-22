package com.example.university.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.*;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    @ManyToMany(mappedBy = "students")
    @JsonIgnoreProperties("students")
    private List<Course> courses = new ArrayList<>();
}