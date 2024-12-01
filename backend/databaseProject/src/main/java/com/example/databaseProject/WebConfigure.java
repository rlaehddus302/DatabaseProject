package com.example.databaseProject;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
@RequiredArgsConstructor
public class WebConfigure implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(final CorsRegistry registry ){
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("PATCH","GET","POST","PUT","DELETE","HEAD","OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}