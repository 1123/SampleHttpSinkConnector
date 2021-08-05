package io.confluent.sample.staticcsvserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class StaticCsvServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StaticCsvServerApplication.class, args);
	}

}
