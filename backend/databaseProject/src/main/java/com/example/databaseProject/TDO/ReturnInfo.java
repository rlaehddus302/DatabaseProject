package com.example.databaseProject.TDO;

import java.util.ArrayList;
import java.util.List;

import com.example.databaseProject.Information.CourseInfo.Session;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ReturnInfo {
	private Session session;
	@JsonIgnore
	private List<TimeBitmask> timeBitmasks = new ArrayList<>();
	
	private String name;
	
	private String curriculum;
	private String area;
	
	public String getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(String curriculum) {
		this.curriculum = curriculum;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Session getSession() {
		return session;
	}
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	
	public List<TimeBitmask> getTimeBitmasks() {
		return timeBitmasks;
	}
	
	public void setTimeBitmasks(List<TimeBitmask> timeBitmasks) {
		this.timeBitmasks = timeBitmasks;
	}
	
	public void add(TimeBitmask timeBitmasks)
	{
		this.timeBitmasks.add(timeBitmasks);
	}
}
