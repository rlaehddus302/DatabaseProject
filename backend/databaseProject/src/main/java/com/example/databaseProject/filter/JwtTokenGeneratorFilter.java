package com.example.databaseProject.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenGeneratorFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 if(authentication != null)
		 {
			 String secret = "ewojpsjcvlefejsljpojxojoejwocdjoir";
			 SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
			 String jwt = Jwts.builder().issuer("AutoTimeTable")
			 				.subject("Jwt Token")
			 				.claim("username", authentication.getName())
			 				.claim("authorities", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
			 						.collect(Collectors.joining(",")))
			 				.issuedAt(new Date())
			 				.expiration(new Date(System.currentTimeMillis() + 3600000))
			 				.signWith(secretKey).compact();
			 response.setHeader("Authorization", jwt);
		 }
		 filterChain.doFilter(request, response);
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		
		return !request.getServletPath().equals("/basicOauth");
	}

}
