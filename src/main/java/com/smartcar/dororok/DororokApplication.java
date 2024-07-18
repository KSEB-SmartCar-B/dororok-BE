package com.smartcar.dororok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DororokApplication {

	public static void main(String[] args) {
		SpringApplication.run(DororokApplication.class, args);
	}

}
