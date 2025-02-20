package com.example.databaseProject.Information;

import java.util.List;

import com.example.databaseProject.Information.CourseInfo.Course;
import com.example.databaseProject.Information.CustomerInfo.Customer;
import com.example.databaseProject.Information.CustomerInfo.MyTimeTableName;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class AcademicTerm {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private int academic_year;
	
	private int semester;

	@OneToMany(mappedBy = "academicTerm", cascade = CascadeType.ALL)
	private List<MyTimeTableName> myTimeTableName;
	
	@OneToMany(mappedBy = "academicTerm", cascade = CascadeType.ALL)
	private List<Course> course;
	
	public AcademicTerm() {};
	
	public AcademicTerm(int academic_year, int semester) {
		super();
		this.academic_year = academic_year;
		this.semester = semester;
	}

	public long getId() {
		return id;
	}

	public int getAcademic_year() {
		return academic_year;
	}

	public void setAcademic_year(int academic_year) {
		this.academic_year = academic_year;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public List<Course> getCourse() {
		return course;
	}
}
