package com.steve.callofthebacklog;

import org.springframework.boot.SpringApplication;

public class TestCallofthebacklogApplication {

	public static void main(String[] args) {
		SpringApplication.from(CallofthebacklogApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
