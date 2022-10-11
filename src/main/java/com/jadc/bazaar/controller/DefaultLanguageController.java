package com.jadc.bazaar.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultLanguageController {

	@GetMapping(value = {"/admin/account", "/"})
	public String customers(Locale locale) {
		String lang = locale.getLanguage();

		return "redirect:/"+lang+"/admin/account";
	}

	@GetMapping(value = {"/admin/notification", "/admin/notification/index"})
	public String notifications(Locale locale) {
		String lang = locale.getLanguage();

		return "redirect:/"+lang+"/admin/notification/index";
	}
}
