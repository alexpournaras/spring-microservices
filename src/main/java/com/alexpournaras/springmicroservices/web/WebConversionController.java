package com.alexpournaras.springmicroservices.web;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebConversionController {

	protected WebRedactionService redactionService;

	protected Logger logger = Logger.getLogger(WebConversionController.class.getName());

	public WebConversionController(WebRedactionService redactionService) {
		this.redactionService = redactionService;
	}

	@RequestMapping("/redact")
	public String doRedaction(@RequestParam(defaultValue="0") String input, Model model) {

		String res = redactionService.redact(input);

		logger.info("Res: " + res);
		model.addAttribute("json", res);

		return "res";
	}



}
