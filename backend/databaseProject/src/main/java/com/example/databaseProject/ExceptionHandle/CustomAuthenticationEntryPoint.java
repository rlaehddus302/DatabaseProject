package com.example.databaseProject.ExceptionHandle;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) 
			throws IOException, ServletException 
	{
			String message = authException.getMessage();
	        LocalDateTime currentTime = LocalDateTime.now();
	        String uri = request.getRequestURI();
	        response.setContentType("application/json");
	        response.setStatus(HttpStatus.UNAUTHORIZED.value());
	        response.setCharacterEncoding("UTF-8");
	        String data = String.format("{\"Time\": \"%s\", \"error\": \"%s\",\"message\": \"%s\",\"path\": \"%s\"}", 
	        		currentTime,HttpStatus.UNAUTHORIZED.getReasonPhrase(),message,uri);
	        response.getWriter().write(data);
	        
	}

}
