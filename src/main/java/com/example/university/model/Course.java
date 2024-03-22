package com.example.university.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Data
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int credits;
    @ManyToOne
    @JoinColumn(name = "professorid")
    private Professor professor;
    @ManyToMany
    @JoinTable(name = "course_student", joinColumns = @JoinColumn(name = "courseid"), inverseJoinColumns = @JoinColumn(name = "studentid"))
    @JsonIgnoreProperties("courses")
    private List<Student> students = new ArrayList<>();
}