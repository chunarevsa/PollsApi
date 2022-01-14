package com.example.pollsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(basePackageClasses = {
	PollsapiApplication.class,
	Jsr310JpaConverters.class
})
public class PollsapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PollsapiApplication.class, args);
	}

}
