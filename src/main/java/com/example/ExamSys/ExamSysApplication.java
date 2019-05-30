package com.example.ExamSys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ExamSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamSysApplication.class, args);
	}

}

