package com.example.databaseProject.Information.CustomerInfo;

import java.util.List;

import com.example.databaseProject.Information.AcademicTerm;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class MyTimeTableName {
	
	@Id
	private String name;
	
	@OneToMany(mappedBy = "name", cascade = CascadeType.REMOVE)
	private List<MyTimeTableSession> sessions;

	@ManyToOne
	@JoinColumn(name = "customerId")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "academicTerm_ID")
	@JsonIgnore
	private AcademicTerm academicTerm;
	
	public MyTimeTableName() {};

	public MyTimeTableName(String name, Customer customer) {
		this.name = name;
		this.customer = customer;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Customer getCustomer() {
		return customer;
	}

	public List<MyTimeTableSession> getSessions() {
		return sessions;
	}

	public void setSessions(List<MyTimeTableSession> sessions) {
		this.sessions = sessions;
	}

	public AcademicTerm getAcademicTerm() {
		return academicTerm;
	}

	public void setAcademicTerm(AcademicTerm academicTerm) {
		this.academicTerm = academicTerm;
	}
}
