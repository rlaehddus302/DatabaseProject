package com.example.databaseProject.Information.CourseInfo;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ClassTimeAndLocation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private int startTime;
	private int endTime;
	private String location;
	private String week;
	
	@JoinColumn(name = "sessionID")
	@JsonBackReference
	@ManyToOne
	private Session session;

	public ClassTimeAndLocation() {};
	
	public ClassTimeAndLocation(int startTime, int endTime, String location, String week, Session session) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.location = location;
		this.week = week;
		this.session = session;
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
