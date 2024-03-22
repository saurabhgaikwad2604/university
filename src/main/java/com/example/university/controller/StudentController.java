package com.example.university.controller;

import com.example.university.model.*;
import com.example.university.service.StudentJpaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class StudentController {
    @Autowired
    public StudentJpaService studentService;

    @GetMapping("/students")
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/students/{studentId}")
    public Student getStudentById(@PathVariable("studentId") int id) {
        return studentService.getStudentById(id);
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping("/students/{studentId}")
    public Student updateStudent(@PathVariable("studentId") int id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    @DeleteMapping("/students/{studentId}")
    public void deleteStudent(@PathVariable("studentId") int id) {
        studentService.deleteStudent(id);

    }

    @GetMapping("/students/{studentId}/courses")
    public List<Course> getStudentCourses(@PathVariable("studentId") int id) {
        return studentService.getStudentCourses(id);
    }
}