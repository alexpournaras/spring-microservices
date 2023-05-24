package com.alexpournaras.springmicroservices.web;

import java.util.List;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import javax.servlet.http.HttpServletResponse;

@Controller
public class WebConversionController {

	protected WebPdfService pdfService;
	protected WebRedactionService redactionService;

	@Autowired
	private DiscoveryClient discoveryClient;

	protected Logger logger = Logger.getLogger(WebConversionController.class.getName());

	public WebConversionController(WebRedactionService redactionService, WebPdfService pdfService) {
		this.redactionService = redactionService;
		this.pdfService = pdfService;
	}

	@PostMapping(value = "/redact")
	public String doRedaction(@RequestParam("text") String text, Model model, HttpServletResponse response) {

		// Send text to redaction service
		String redactedJson = redactionService.redact(text);
		JSONObject jsonObject = new JSONObject(redactedJson);
		String redacted = jsonObject.getString("response");

		// Send the redacted text to pdf service and get its saved path
		String path = pdfService.pdf(redacted);
		String serviceUrl = getServiceUrl("pdf-service");

		// Create the response model
		model.addAttribute("original", text);
		model.addAttribute("path", serviceUrl + "/pdf/" + path);

		return "redacted";
	}
	
	private String getServiceUrl(String serviceName) {
		List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);

		// Return the url for the first found service instance
		if (instances != null && !instances.isEmpty()) {
			return instances.get(0).getUri().toString();
		}
	
		throw new RuntimeException("Could not find service: " + serviceName);
	}


}
