package com.example.databaseProject.Information.CustomerInfo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Customer {
	
	@Id
	private String id;
	private long studentNumber;
	private String password;
	private String name;
	@JsonIgnore
	private String role;
	
	@OneToMany(mappedBy = "customer")
	private List<MyTimeTableName> tableNames;
	
    public Customer() {
    }

	public Customer(String id, long studentNumber, String password, String name, String role) {
		this.id = id;
		this.studentNumber = studentNumber;
		this.password = password;
		this.name = name;
		this.role = role;
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

	public String getRole() {
		return role;
	}
	
	public List<MyTimeTableName> getTableNames() {
		return tableNames;
	}
}
