package com.example.university.repository;

import com.example.university.model.*;
import java.util.List;

public interface ProfessorRepository {
    List<Professor> getProfessors();

    Professor getProfessorById(int id);

    Professor addProfessor(Professor professor);

    Professor updateProfessor(int id, Professor professor);

    void deleteProfessor(int id);

    List<Course> getProfessorCourses(int id);
}