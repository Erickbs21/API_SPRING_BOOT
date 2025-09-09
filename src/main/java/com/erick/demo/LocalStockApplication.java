package com.erick.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LocalStockApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalStockApplication.class, args);
        System.out.println("API BACKEND");
	}

}
