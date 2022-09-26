package com.jadc.bazaar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultLanguageController {

	@GetMapping("/home")
	public String home() {
		return "home";
	}
}
