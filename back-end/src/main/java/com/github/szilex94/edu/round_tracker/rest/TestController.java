package com.github.szilex94.edu.round_tracker.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/stuff")
public class TestController {

	@GetMapping("/test")
	public Mono<String> getTest() {
		return Mono.just("Hello From RT");
	}

}
