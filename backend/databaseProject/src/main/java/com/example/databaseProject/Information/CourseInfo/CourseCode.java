package com.example.databaseProject.Information.CourseInfo;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class CourseCode {
	@Id
	private String code;

	@OneToOne
	@JsonBackReference
	@JoinColumn(name = "name")
	private Course course;

	public CourseCode() {};
	
	public String getCode() {
		return code;
	}

	public Course getCourse() {
		return course;
	}

	
}
