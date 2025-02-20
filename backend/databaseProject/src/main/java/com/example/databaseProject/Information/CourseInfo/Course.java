package com.example.databaseProject.Information.CourseInfo;

import java.util.ArrayList;
import java.util.List;

import com.example.databaseProject.Information.AcademicTerm;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import jakarta.persistence.OneToOne;

@Entity
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "name", unique = true)
	private String name;

	@Column(name = "credit")
	private int credit;
	
	@Column(name = "code", unique = true)
	private String code;
	
	private String curriculum;
	private String area;

	@ManyToOne
	@JoinColumn(name = "academicTerm_ID")
	@JsonIgnore
	private AcademicTerm academicTerm;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
	private List<Session> session = new ArrayList<Session>();
	
	public Course() {};
	
	public Course(String name, int credit, String code, String curriculum, String area,
			AcademicTerm academicTerm, List<Session> session) {
		this.name = name;
		this.credit = credit;
		this.code = code;
		this.curriculum = curriculum;
		this.area = area;
		this.academicTerm = academicTerm;
		this.session = session;
	}

	public AcademicTerm getAcademicTerm() {
		return academicTerm;
	}


	public void setAcademicTerm(AcademicTerm academicTerm) {
		this.academicTerm = academicTerm;
	}

	public String getName() {
		return name;
	}

	public int getCredit() {
		return credit;
	}

	public long getId() {
		return id;
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
	
	public String getCode() {
		return code;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCurriculum(String curriculum) {
		this.curriculum = curriculum;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setSession(List<Session> session) {
		this.session = session;
	}
	
	
}
