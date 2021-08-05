package io.confluent.samples.httpsink.restservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class HttpSinkRestServiceApp {

	public static void main(String[] args) {
		SpringApplication.run(HttpSinkRestServiceApp.class, args);
	}

}

@RestController
@Slf4j
class MyController {

	@PostMapping("/api/{id}")
	public void receive(@PathVariable String id, @RequestBody String body) {
		log.info("received {}, {}", id, body);
		if (Integer.parseInt(id) % 2 == 0) { throw new RuntimeException("Not accepting even IDs"); }
	}

}