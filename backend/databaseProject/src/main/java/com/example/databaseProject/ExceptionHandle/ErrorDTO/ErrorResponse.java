package com.example.databaseProject.ExceptionHandle.ErrorDTO;

import java.time.LocalDateTime;

public class ErrorResponse {
	
	public String message;
    public LocalDateTime currentTime;
    public String uri;
    int error;
    
	public ErrorResponse(String message, LocalDateTime currentTime, String uri, int error) {
		this.message = message;
		this.currentTime = currentTime;
		this.uri = uri;
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public LocalDateTime getCurrentTime() {
		return currentTime;
	}

	public String getUri() {
		return uri;
	}

	public int getError() {
		return error;
	}
}
