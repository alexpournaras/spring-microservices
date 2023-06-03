package com.alexpournaras.springmicroservices.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
public class WebServer {

	public static final String PDF_SERVICE_URL = "http://pdf-service";
	public static final String REDACTION_SERVICE_URL = "http://redaction-service";

	@Autowired
    private static ApplicationContext applicationContext;
	
	public static void main(String[] args) {
		System.setProperty("spring.config.name", "web-server");
		applicationContext = SpringApplication.run(WebServer.class, args);
	}

	@LoadBalanced 
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public WebRedactionService redactionService() {
		return new WebRedactionService(REDACTION_SERVICE_URL);
	}

	@Bean
	public WebPdfService pdfService() {
		return new WebPdfService(PDF_SERVICE_URL);
	}

	@Bean
	public WebConversionController webController() {
		return new WebConversionController(redactionService(), pdfService());
	}

	@Bean
	public HomeController homeController() {
		return new HomeController();
	}

}