package com.example.databaseProject.Information;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Course {
	@Id
	@Column(name = "name")
	private String name;

	@Column(name = "credit")
	private int credit;
	
	private String curriculum;
	private String area;
	
	@JsonManagedReference
	@OneToOne(mappedBy = "course")
	private CourseCode courseCode;
	@JsonManagedReference
	@OneToMany(mappedBy = "course")
	private List<Session> session = new ArrayList<Session>();
	public Course() {};
	
	public String getName() {
		return name;
	}

	public int getCredit() {
		return credit;
	}

	public CourseCode getCourseCode() {
		return courseCode;
	}

	public List<Session> getSession() {
		return session;
	}
	
	public String getCurriculum() {
		return curriculum;
	}

	public String getArea() {
		return area;
	}
	
}
