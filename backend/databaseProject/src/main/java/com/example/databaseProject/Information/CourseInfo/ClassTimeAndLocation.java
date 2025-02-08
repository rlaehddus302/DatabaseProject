package com.example.databaseProject.Information.CourseInfo;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ClassTimeAndLocation {
	@Id
	private String id;
	
	private int startTime;
	private int endTime;
	private String location;
	private String week;
	
	@JoinColumn(name = "courseId")
	@JsonBackReference
	@ManyToOne
	private Session session;

	public String getId() {
		return id;
	}

	public int getStart() {
		return startTime;
	}

	public int getEnd() {
		return endTime;
	}

	public String getLocation() {
		return location;
	}

	public String getWeek() {
		return week;
	}

	public Session getSession() {
		return session;
	}
}
