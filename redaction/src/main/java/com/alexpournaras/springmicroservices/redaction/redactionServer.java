package com.alexpournaras.springmicroservices.redaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class redactionServer {
	public static void main(String[] args) {
		System.setProperty("spring.config.name", "redaction-server");
		SpringApplication.run(redactionServer.class, args);
	}
}
