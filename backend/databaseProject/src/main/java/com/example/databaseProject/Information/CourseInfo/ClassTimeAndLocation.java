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
	
	private int startTime = -1;
	private int endTime = -1;
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
	
	public long getId() {
		return id;
	}

	public int getStartTime() {
		return startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public String getLocation() {
		return location;
	}

	public String getWeek() {
		return week;
	}
	
	public void setSession(Session session) {
		this.session = session;
	}

	public Session getSession() {
		return session;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setWeek(String week) {
		this.week = week;
	}


}
