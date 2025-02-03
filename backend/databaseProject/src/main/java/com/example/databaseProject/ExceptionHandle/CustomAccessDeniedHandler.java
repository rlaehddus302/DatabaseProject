package com.example.databaseProject.ExceptionHandle;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) 
			throws IOException, ServletException 
	{
		String message = accessDeniedException.getMessage();
        LocalDateTime currentTime = LocalDateTime.now();
        String uri = request.getRequestURI();
        response.setContentType("application/json");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setCharacterEncoding("UTF-8");
        String data = String.format("{\"Time\": \"%s\", \"error\": \"%s\",\"message\": \"%s\",\"path\": \"%s\"}", 
        		currentTime,HttpStatus.FORBIDDEN.getReasonPhrase(),message,uri);
        response.getWriter().write(data);
	}

}
