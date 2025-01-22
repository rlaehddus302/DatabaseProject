package com.example.databaseProject.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletResponse;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfigure {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(t -> t.disable())
		.cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 필요한 경우 세션 생성
            )
		.authorizeHttpRequests((requests) -> 
		requests.requestMatchers("/register","/error").permitAll()
				.requestMatchers("/basicOauth",
				"/caculate","/SetCondition","/table","/course","/exit","/courseSearch").authenticated());
		http.formLogin(withDefaults());
        http.httpBasic(httpBasic -> httpBasic
                .authenticationEntryPoint((request, response, authException) -> {
                    // WWW-Authenticate 헤더를 제거하여 기본 로그인 창 비활성화
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Unauthorized"); // 커스텀 메시지 반환
                })
            );
		return http.build();
	}
	
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:5173"); // 허용할 Origin
        configuration.addAllowedMethod("*"); // 모든 HTTP 메서드 허용
        configuration.addAllowedHeader("*"); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 쿠키 사용을 허용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 URL에 대해 CORS 적용
        return source;
    }
	
    @Bean
    public PasswordEncoder passwordEncoder()
    {
    	return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    
}