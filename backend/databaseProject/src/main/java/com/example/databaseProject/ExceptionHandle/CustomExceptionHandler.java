package com.example.databaseProject.ExceptionHandle;

import java.net.URI;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.databaseProject.ExceptionHandle.CustomException.CourseNotFoundException;
import com.example.databaseProject.ExceptionHandle.ErrorDTO.ErrorResponse;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(CourseNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleCourseNotFoundException(CourseNotFoundException ex)
	{
        URI profilePictureUri = URI.create("http://localhost:5173/editSubject");
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),LocalDateTime.now(), profilePictureUri.toString(), 
				HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}
}
