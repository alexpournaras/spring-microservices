package com.alexpournaras.springmicroservices.pdf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PdfServer {
	public static void main(String[] args) {
		System.setProperty("spring.config.name", "pdf-server");
		SpringApplication.run(PdfServer.class, args);
	}
}
