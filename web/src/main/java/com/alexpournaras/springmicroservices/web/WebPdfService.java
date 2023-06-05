package com.alexpournaras.springmicroservices.web;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

	public ResponseEntity<byte[]> pdf(String text) {
		Map<String, String> map = new HashMap<>();
		map.put("text", text);

		// Create json with the text
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, String>> textToPdf = new HttpEntity<>(map, headers);

		return restTemplate.exchange(serviceUrl + "/pdf", HttpMethod.POST, textToPdf, byte[].class);
	}

}

