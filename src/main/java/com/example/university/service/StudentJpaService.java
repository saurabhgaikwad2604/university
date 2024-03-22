package com.example.university.service;

import com.example.university.model.*;
import com.example.university.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@Service
public class StudentJpaService implements StudentRepository {
    @Autowired
    private StudentJpaRepository studentJpaRepository;
    @Autowired
    private CourseJpaRepository courseJpaRepository;

    @Override
    public List<Student> getStudents() {
        List<Student> students = studentJpaRepository.findAll();
        return students;
    }

    @Override
    public Student getStudentById(int id) {
        try {
            Student student = studentJpaRepository.findById(id).get();
            return student;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Student addStudent(Student student) {
        try {
            List<Integer> courseIds = new ArrayList<>();
            for (Course course : student.getCourses()) {
                courseIds.add(course.getId());
            }
            List<Course> courses = courseJpaRepository.findAllById(courseIds);
            if (courseIds.size() != courses.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            student.setCourses(courses);

            for (Course course : courses) {
                course.getStudents().add(student);
            }
            Student savedStudent = studentJpaRepository.save(student);
            courseJpaRepository.saveAll(courses);
            return savedStudent;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Student updateStudent(int id, Student student) {
        try {
            Student newStudent = studentJpaRepository.findById(id).get();
            if (student.getName() != null) {
                newStudent.setName(student.getName());
            }
            if (student.getEmail() != null) {
                newStudent.setEmail(student.getEmail());
            }
            if (student.getCourses() != null) {
                List<Course> courses = newStudent.getCourses();
                for (Course course : courses) {
                    course.getStudents().remove(newStudent);
                }
                courseJpaRepository.saveAll(courses);

                List<Integer> newCourseIds = new ArrayList<>();
                for (Course course : student.getCourses()) {
                    newCourseIds.add(course.getId());
                }
                List<Course> newCourses = courseJpaRepository.findAllById(newCourseIds);
                if (newCourseIds.size() != newCourses.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }

                for (Course course : newCourses) {
                    course.getStudents().add(newStudent);
                }
                courseJpaRepository.saveAll(newCourses);
                newStudent.setCourses(newCourses);
            }
            return studentJpaRepository.save(newStudent);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteStudent(int id) {
        try {
            Student student = studentJpaRepository.findById(id).get();

            List<Course> courses = student.getCourses();
            for (Course course : courses) {
                course.getStudents().remove(student);
            }
            courseJpaRepository.saveAll(courses);

            studentJpaRepository.deleteById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Course> getStudentCourses(int id) {
        try {
            Student student = studentJpaRepository.findById(id).get();
            List<Course> courses = student.getCourses();
            return courses;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}