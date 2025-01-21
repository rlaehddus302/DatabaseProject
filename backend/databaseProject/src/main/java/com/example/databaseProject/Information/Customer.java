package com.example.databaseProject.Information;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Customer {
	
	@Id
	private String id;
	private String password;
	
	public Customer(String id, String password) {
		super();
		this.id = id;
		this.password = password;
	}
	public String getId() {
		return id;
	}
	public String getPassword() {
		return password;
	}
	
}
