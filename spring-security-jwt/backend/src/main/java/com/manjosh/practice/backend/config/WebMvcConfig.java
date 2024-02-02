package com.manjosh.practice.backend.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry){
        corsRegistry.addMapping("/**").allowCredentials(true)
                .allowedOrigins("http://localhost:5173")
                .allowedHeaders(
                        HttpHeaders.AUTHORIZATION,
                        HttpHeaders.CONTENT_TYPE,
                        HttpHeaders.ACCEPT
                )
                .allowedMethods(HttpMethod.GET.name(),HttpMethod.POST.name(),HttpMethod.PUT.name(),HttpMethod.DELETE.name())
                .maxAge(3600L);
    }
}
