package com.jadc.bazaar.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jadc.bazaar.entity.Customer;
import com.jadc.bazaar.service.CustomerService;

@Controller
@RequestMapping("/admin/account")
public class CustomerController {

	private final CustomerService customerService;

	@Autowired
	public CustomerController(CustomerService service) {
		this.customerService = service;
	}

	@GetMapping("")
	public String listAll(Model model) {
		return listAllWithPagination(0, "", model);
	}

	@GetMapping("/{offset}")
	public String listAllWithPagination(@PathVariable("offset") int offset, @RequestParam(required = false) String companyName, Model model) {
		Page<Customer> customers = null;
		int pageSize = 0;
		int totalPages = 0;
		long totalEntries = 0;
		int firstIndexOnPage = 0;
		int lastIndexOnPage = 0;

		if (companyName.isEmpty() || companyName == null) {
			customers = customerService.findAll(offset);
		} else {
			customers = customerService.search(companyName, offset);
		}

		if (customers != null) {
			pageSize = customers.getSize();
			totalPages = customers.getTotalPages();
			totalEntries = customers.getTotalElements();
			firstIndexOnPage = pageSize * offset + 1;
			lastIndexOnPage = pageSize * offset + pageSize;
		}

		model.addAttribute("customers", customers);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalEntries", totalEntries);
		model.addAttribute("firstIndexOnPage", firstIndexOnPage);
		model.addAttribute("lastIndexOnPage", lastIndexOnPage);
		model.addAttribute("offset", offset);

		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		return "customers";
	}

	@GetMapping("/add")
	public String showAddCustomerForm(Model model) {
		Customer customer = new Customer();
		model.addAttribute("customer", customer);

		return "customer-form";
	}

	@GetMapping("/update")
	public String showCustomerFormForUpdate(@RequestParam("customerId") int id, Model model) {
		Customer customer = customerService.findById(id);
		model.addAttribute("customer", customer);

		return "customer-form";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute("customer") Customer customer) {
		customerService.save(customer);

		return "redirect:";
	}

	@PostMapping("/delete")
	public String deleteRows(@RequestParam("customers") Integer[] customers) {
		customerService.deleteSelectedRows(Arrays.asList(customers));

		return "redirect:";
	}
}
