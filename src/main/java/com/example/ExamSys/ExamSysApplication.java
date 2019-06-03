package com.example.ExamSys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ExamSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamSysApplication.class, args);
	}

	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer(){
		return new EmbeddedServletContainerCustomizer() {
			@Override
			 public void customize(ConfigurableEmbeddedServletContainer container) {
 
				 container.setSessionTimeout(3*60*60);//单位为S
            }
        };
    }

}

