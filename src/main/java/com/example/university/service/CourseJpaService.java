package com.example.university.service;

import com.example.university.model.*;
import com.example.university.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@Service
public class CourseJpaService implements CourseRepository {
    @Autowired
    private CourseJpaRepository courseJpaRepository;
    @Autowired
    private StudentJpaRepository studentJpaRepository;
    @Autowired
    private ProfessorJpaRepository professorJpaRepository;

    @Override
    public List<Course> getCourses() {
        List<Course> courses = courseJpaRepository.findAll();
        return courses;
    }

    @Override
    public Course getCourseById(int id) {
        try {
            Course course = courseJpaRepository.findById(id).get();
            return course;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Course addCourse(Course course) {
        List<Integer> studentIds = new ArrayList<>();
        for (Student student : course.getStudents()) {
            studentIds.add(student.getId());
        }

        Professor professor = course.getProfessor();
        int professorId = professor.getId();
        try {
            List<Student> students = studentJpaRepository.findAllById(studentIds);
            if (studentIds.size() != students.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            course.setStudents(students);

            Professor newProfessor = professorJpaRepository.findById(professorId).get();
            course.setProfessor(newProfessor);
            courseJpaRepository.save(course);
            return course;

        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Course updateCourse(int id, Course course) {
        try {
            Course newCourse = courseJpaRepository.findById(id).get();
            if (course.getName() != null) {
                newCourse.setName(course.getName());
            }
            if (course.getCredits() != 0) {
                newCourse.setCredits(course.getCredits());
            }
            if (course.getProfessor() != null) {
                Professor professor = course.getProfessor();
                int professorId = professor.getId();
                Professor newProfessor = professorJpaRepository.findById(professorId).get();
                newCourse.setProfessor(newProfessor);
            }
            if (course.getStudents() != null) {
                List<Student> students = newCourse.getStudents();
                for (Student student : students) {
                    student.getCourses().remove(newCourse);
                }
                studentJpaRepository.saveAll(students);

                List<Integer> newStudentIds = new ArrayList<>();
                for (Student student : course.getStudents()) {
                    newStudentIds.add(student.getId());
                }
                List<Student> newStudents = studentJpaRepository.findAllById(newStudentIds);
                if (newStudentIds.size() != newStudents.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }

                for (Student student : newStudents) {
                    student.getCourses().add(newCourse);
                }
                studentJpaRepository.saveAll(newStudents);
                newCourse.setStudents(newStudents);
            }
            courseJpaRepository.save(newCourse);
            return newCourse;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteCourse(int id) {
        try {
            Course course = courseJpaRepository.findById(id).get();

            List<Student> students = course.getStudents();
            for (Student student : students) {
                student.getCourses().remove(course);
            }
            studentJpaRepository.saveAll(students);

            courseJpaRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public Professor getCourseProfessor(int id) {
        try {
            Course course = courseJpaRepository.findById(id).get();
            Professor professor = course.getProfessor();
            return professor;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Student> getCourseStudents(int id) {
        try {
            Course course = courseJpaRepository.findById(id).get();
            List<Student> students = course.getStudents();
            return students;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}