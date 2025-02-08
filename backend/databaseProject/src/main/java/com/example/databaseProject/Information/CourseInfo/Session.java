package com.example.databaseProject.Information.CourseInfo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Session {
	
	@Id
	private String courseID;
	
	@JoinColumn(name = "name")
	@JsonBackReference
	@ManyToOne
	private Course course;

	@JsonManagedReference
	@OneToMany(mappedBy = "session")
	private List<ClassTimeAndLocation> classTimeAndLocation = new ArrayList<ClassTimeAndLocation>();
	
	private String remarks;
	
	private String professor;
	
	public String getRemarks() {
		return remarks;
	}

	public String getProfessor() {
		return professor;
	}

	public String getCourseID() {
		return courseID;
	}

	public Course getCourse() {
		return course;
	}

	public List<ClassTimeAndLocation> getClassTimeAndLocation() {
		return classTimeAndLocation;
	}
	
	
	
}
