package com.alexpournaras.springmicroservices.redaction;

import java.util.logging.Logger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class redactionController {
	protected Logger logger = Logger.getLogger(redactionController.class.getName());

	@RequestMapping("/redact")
	public String doRedaction(@RequestParam(defaultValue="0") String input) {

		// todo

		return "{\"input\":\"" + input + "\", \"res\": \"" + "test" + "\"}";
	}

}
