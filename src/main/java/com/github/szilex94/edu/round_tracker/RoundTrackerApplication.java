package com.github.szilex94.edu.round_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.github.szilex94.edu.round_tracker")
@EnableAutoConfiguration
public class RoundTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoundTrackerApplication.class, args);
	}

}
