package com.alexpournaras.springmicroservices.web;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebConversionController {

	protected WebRedactionService redactionService;

	protected Logger logger = Logger.getLogger(WebConversionController.class.getName());

	public WebConversionController(WebRedactionService redactionService) {
		this.redactionService = redactionService;
	}

	@RequestMapping(value = "/redact", method = RequestMethod.POST)
	public String doRedaction(@RequestParam("text") String text, Model model) {
		String response = redactionService.redact(text);

		logger.info("Res: " + response);
		model.addAttribute("response", response);

		return "redacted";
	}

}
