package com.example.university.controller;

import com.example.university.model.*;
import com.example.university.service.CourseJpaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class CourseController {
	@Autowired
	public CourseJpaService courseService;

	@GetMapping("/courses")
	public List<Course> getCourses() {
		return courseService.getCourses();
	}

	@GetMapping("/courses/{courseId}")
	public Course getCourseById(@PathVariable("courseId") int id) {
		return courseService.getCourseById(id);
	}

	@PostMapping("/courses")
	public Course addCourse(@RequestBody Course course) {
		return courseService.addCourse(course);
	}

	@PutMapping("/courses/{courseId}")
	public Course updateCourse(@PathVariable("courseId") int id, @RequestBody Course course) {
		return courseService.updateCourse(id, course);
	}

	@DeleteMapping("/courses/{courseId}")
	public void deleteCourse(@PathVariable("courseId") int id) {
		courseService.deleteCourse(id);
	}

	@GetMapping("/courses/{courseId}/professor")
	public Professor getCourseProfessor(@PathVariable("courseId") int id) {
		return courseService.getCourseProfessor(id);
	}

	@GetMapping("/courses/{courseId}/students")
	public List<Student> getCourseStudents(@PathVariable("courseId") int id) {
		return courseService.getCourseStudents(id);
	}

}