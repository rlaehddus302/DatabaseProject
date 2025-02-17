package com.example.databaseProject.Information.CourseInfo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Session {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@JoinColumn(name = "courseID")
	@JsonBackReference
	@ManyToOne
	private Course course;

	@JsonManagedReference
	@OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
	private List<ClassTimeAndLocation> classTimeAndLocation = new ArrayList<ClassTimeAndLocation>();
	
	private String remarks;
	
	private String professor;
	
	private String sessionCODE;
	
	public Session() {}
	
	public Session(Course course, List<ClassTimeAndLocation> classTimeAndLocation, String remarks, String professor,
			String sessionCODE) {
		this.course = course;
		this.classTimeAndLocation = classTimeAndLocation;
		this.remarks = remarks;
		this.professor = professor;
		this.sessionCODE = sessionCODE;
	}
	
	public long getId() {
		return id;
	}

	public String getRemarks() {
		return remarks;
	}

	public String getProfessor() {
		return professor;
	}

	public Course getCourse() {
		return course;
	}

	public List<ClassTimeAndLocation> getClassTimeAndLocation() {
		return classTimeAndLocation;
	}

	public String getSessionCODE() {
		return sessionCODE;
	}

	public void setClassTimeAndLocation(List<ClassTimeAndLocation> classTimeAndLocation) {
		this.classTimeAndLocation = classTimeAndLocation;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setProfessor(String professor) {
		this.professor = professor;
	}

	public void setSessionCODE(String sessionCODE) {
		this.sessionCODE = sessionCODE;
	}
	
	
}
