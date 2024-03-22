package com.example.university.service;

import com.example.university.model.*;
import com.example.university.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@Service
public class ProfessorJpaService implements ProfessorRepository {
    @Autowired
    private ProfessorJpaRepository professorJpaRepository;
    @Autowired
    private CourseJpaRepository courseJpaRepository;

    @Override
    public List<Professor> getProfessors() {
        List<Professor> professors = professorJpaRepository.findAll();
        return professors;
    }

    @Override
    public Professor getProfessorById(int id) {
        try {
            Professor professor = professorJpaRepository.findById(id).get();
            return professor;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Professor addProfessor(Professor professor) {
        professorJpaRepository.save(professor);
        return professor;
    }

    @Override
    public Professor updateProfessor(int id, Professor professor) {
        try {
            Professor newProfessor = professorJpaRepository.findById(id).get();
            if (professor.getName() != null) {
                newProfessor.setName(professor.getName());
            }
            if (professor.getDepartment() != null) {
                newProfessor.setDepartment(professor.getDepartment());
            }
            return professorJpaRepository.save(newProfessor);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteProfessor(int id) {
        try {
            Professor professor = professorJpaRepository.findById(id).get();

            List<Course> courses = courseJpaRepository.findByProfessor(professor);
            for (Course course : courses) {
                course.setProfessor(null);
            }
            courseJpaRepository.saveAll(courses);

            professorJpaRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Course> getProfessorCourses(int id) {
        try {
            Professor professor = professorJpaRepository.findById(id).get();
            List<Course> courses = courseJpaRepository.findByProfessor(professor);
            return courses;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
