package com.example.databaseProject.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.databaseProject.ExceptionHandle.CustomAccessDeniedHandler;
import com.example.databaseProject.ExceptionHandle.CustomAuthenticationEntryPoint;
import com.example.databaseProject.filter.JwtTokenGeneratorFilter;
import com.example.databaseProject.filter.JwtTokenValidationFilter;

import jakarta.servlet.http.HttpServletResponse;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.List;

@Configuration
public class SecurityConfigure {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(t -> t.disable())
		.cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
            )
        .addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
        .addFilterBefore(new JwtTokenValidationFilter(), BasicAuthenticationFilter.class)
		.authorizeHttpRequests((requests) -> 
		requests.requestMatchers("/register","/error","/idDuplicate").permitAll()
				.requestMatchers("/updateCourse","/deleteCourse","/updateSession","/csv","/adminCourse/*","/adminCourse","/deleteSession").hasRole("ADMIN")
				.requestMatchers("/basicOauth","/myTable","/updateName","/deleteTable","/course",
				"/caculate","/SetCondition","/table","/exit","/courseSearch","/storeMyTimeTable").hasAnyRole("ADMIN","USER"));
		http.formLogin(withDefaults());
        http.httpBasic(httpBasic -> httpBasic
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            );
        http.exceptionHandling(t -> t.authenticationEntryPoint(new CustomAuthenticationEntryPoint()));
        http.exceptionHandling(t -> t.accessDeniedHandler(new CustomAccessDeniedHandler()));
		return http.build();
	}
	
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:5173"); // 허용할 Origin
        configuration.addAllowedMethod("*"); // 모든 HTTP 메서드 허용
        configuration.addAllowedHeader("*"); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 쿠키 사용을 허용
        configuration.setExposedHeaders(List.of("Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 URL에 대해 CORS 적용
        return source;
    }
	
    @Bean
    public PasswordEncoder passwordEncoder()
    {
		return NoOpPasswordEncoder.getInstance();
    }
    
}