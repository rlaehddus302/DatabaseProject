package com.example.databaseProject.ExceptionHandle.CustomException;

public class CourseNotFoundException extends RuntimeException {

	private int id;
	
	public CourseNotFoundException(String message, int id) {
		super(message);
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
