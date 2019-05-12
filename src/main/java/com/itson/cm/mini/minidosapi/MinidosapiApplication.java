package com.itson.cm.mini.minidosapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class MinidosapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinidosapiApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/checkinUser").allowedOrigins("http://localhost:8080");
				registry.addMapping("/api/removehost").allowedOrigins("http://localhost:8080");
				registry.addMapping("/api/currenthost").allowedOrigins("http://localhost:8080");
			}
		};
	}

}
