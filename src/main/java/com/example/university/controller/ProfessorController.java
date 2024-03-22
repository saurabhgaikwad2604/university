package com.example.university.controller;

import com.example.university.model.*;
import com.example.university.service.ProfessorJpaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ProfessorController {
	@Autowired
	public ProfessorJpaService professorService;

	@GetMapping("/professors")
	public List<Professor> getProfessors() {
		return professorService.getProfessors();
	}

	@GetMapping("/professors/{professorId}")
	public Professor getProfessorById(@PathVariable("professorId") int id) {
		return professorService.getProfessorById(id);
	}

	@PostMapping("/professors")
	public Professor addProfessor(@RequestBody Professor professor) {
		return professorService.addProfessor(professor);
	}

	@PutMapping("/professors/{professorId}")
	public Professor updateProfessor(@PathVariable("professorId") int id, @RequestBody Professor professor) {
		return professorService.updateProfessor(id, professor);
	}

	@DeleteMapping("/professors/{professorId}")
	public void deleteProfessor(@PathVariable("professorId") int id) {
		professorService.deleteProfessor(id);
	}

	@GetMapping("/professors/{professorId}/courses")
	public List<Course> getProfessorCourses(@PathVariable("professorId") int id) {
		return professorService.getProfessorCourses(id);
	}

}