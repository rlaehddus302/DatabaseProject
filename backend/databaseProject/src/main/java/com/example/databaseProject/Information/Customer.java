package com.example.databaseProject.Information;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Customer {
	
	@Id
	private String id;
	private long studentNumber;
	private String password;
	private String name;
	
    public Customer() {
    }
	
	public Customer(String id, long studentNumber, String password, String name) {
		this.id = id;
		this.studentNumber = studentNumber;
		this.password = password;
		this.name = name;
	}
	public long getStudentNumber() {
		return studentNumber;
	}
	public String getName() {
		return name;
	}
	public String getId() {
		return id;
	}
	public String getPassword() {
		return password;
	}
	
}
