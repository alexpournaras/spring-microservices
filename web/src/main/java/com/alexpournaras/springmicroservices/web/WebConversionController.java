package com.alexpournaras.springmicroservices.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ResponseEntity<byte[]> doRedaction(@RequestParam("text") String text, Model model, HttpServletResponse response) {

		// Send text to redaction service
		Map<String, String> redactedResponse = redactionService.redact(text);
		String redactedText = redactedResponse.get("response");
		
		// Hashing URL
		String hashingUrl = redactedResponse.get("hashing_url");
		System.out.println("Hashing URL: " + hashingUrl);

		// Send the redacted text to the pdf service and get the pdf as byte array
		ResponseEntity<byte[]> pdfResponse = pdfService.pdf(redactedText);

		String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String pdfFileName = timeStamp + "_redacted.pdf";

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + pdfFileName);
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

		return new ResponseEntity<>(pdfResponse.getBody(), headers, HttpStatus.OK);
	}

}
