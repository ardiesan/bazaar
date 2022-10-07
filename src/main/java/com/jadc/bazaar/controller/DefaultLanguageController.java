package com.jadc.bazaar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultLanguageController {

	@GetMapping("/admin/account")
	public String customers() {
		return "redirect:/ja/admin/account";
	}

	@GetMapping("/")
	public String root() {
		return "redirect:/ja/admin/account";
	}

	@GetMapping("/admin/notification")
	public String notificationImplicitIndexMapping() {
		return "redirect:/en/admin/notification/index";
	}

	@GetMapping("/admin/notification/index")
	public String notificationExplicitIndexMapping() {
		return "redirect:/en/admin/notification/index";
	}
}
