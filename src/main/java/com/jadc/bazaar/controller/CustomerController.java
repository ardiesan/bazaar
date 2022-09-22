package com.jadc.bazaar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jadc.bazaar.entity.Customer;
import com.jadc.bazaar.service.CustomerService;

@Controller
@RequestMapping("/admin/account")
public class CustomerController {

	private final CustomerService customerService;

	@Autowired
	public CustomerController(CustomerService service){
		this.customerService = service;
	}

	@GetMapping("/list")
	public String listAll(Model model) {
		List<Customer> customers = customerService.findAll();
		model.addAttribute("customers", customers);

		return "customers";
	}

	@GetMapping("/customerForm")
	public String showAddCustomerForm(Model model) {
		Customer customer = new Customer();
		model.addAttribute("customer", customer);

		return "customer-form";
	}

	@GetMapping("/customerFormForUpdate")
	public String showCustomerFormForUpdate(@RequestParam("customerId") int id, Model model) {
		Customer customer = customerService.findById(id);
		model.addAttribute("customer", customer);

		return "customer-form";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute("customer") Customer customer) {
		customerService.save(customer);

		return "redirect:list";
	}

	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int id, RedirectAttributes redirAttrs) {
		customerService.deleteById(id);

		return "redirect:list";
	}
}
