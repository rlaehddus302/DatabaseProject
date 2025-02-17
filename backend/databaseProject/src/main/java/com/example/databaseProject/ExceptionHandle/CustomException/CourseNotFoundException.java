package com.example.databaseProject.ExceptionHandle.CustomException;

public class CourseNotFoundException extends RuntimeException {

	public CourseNotFoundException(String message) {
		super(message);
	}

}
