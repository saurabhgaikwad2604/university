package com.example.university.repository;

import com.example.university.model.*;
import java.util.List;

public interface CourseRepository {
    List<Course> getCourses();

    Course getCourseById(int id);

    Course addCourse(Course course);

    Course updateCourse(int id, Course course);

    void deleteCourse(int id);

    Professor getCourseProfessor(int id);

    List<Student> getCourseStudents(int id);
}