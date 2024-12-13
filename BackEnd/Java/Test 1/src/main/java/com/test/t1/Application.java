package com.test.t1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		System.out.println("VARIABLE: " + System.getProperty("user.dir"));
		SpringApplication.run(Application.class, args); 
	}
}