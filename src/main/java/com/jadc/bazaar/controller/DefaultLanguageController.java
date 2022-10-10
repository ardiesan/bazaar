package com.jadc.bazaar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultLanguageController {

	@GetMapping(value = {"/admin/account", "/"})
	public String customers() {
		return "redirect:/ja/admin/account";
	}

	@GetMapping(value = {"/admin/notification", "/admin/notification/index"})
	public String notifications() {
		return "redirect:/ja/admin/notification/index";
	}
}
