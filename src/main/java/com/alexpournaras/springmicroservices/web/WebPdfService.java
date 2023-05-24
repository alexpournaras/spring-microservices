package com.alexpournaras.springmicroservices.web;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;

@Service
public class WebPdfService {
	
	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;
	
	protected String serviceUrl;
	protected Logger logger = Logger.getLogger(WebPdfService.class.getName());

	public WebPdfService(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl : "http://" + serviceUrl;
	}

	public String pdf(String text) {
		TextInput input = new TextInput();
		input.setText(text);

		// Create json with the input text
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<TextInput> entity = new HttpEntity<>(input, headers);

		return restTemplate.postForObject(serviceUrl + "/pdf", entity, String.class);
	}

}

