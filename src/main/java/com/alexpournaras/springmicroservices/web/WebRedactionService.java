package com.alexpournaras.springmicroservices.web;

import java.util.Map;
import java.util.HashMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;

@Service
public class WebRedactionService {
	
	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;
	
	protected String serviceUrl;
	protected Logger logger = Logger.getLogger(WebRedactionService.class.getName());

	public WebRedactionService(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl : "http://" + serviceUrl;
	}

	public String redact(String text) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> map = new HashMap<>();
		map.put("text", text);

		HttpEntity<Map<String, String>> textToRedact = new HttpEntity<>(map, headers);

		return restTemplate.postForObject(serviceUrl + "/redact", textToRedact, String.class);
	}

}

