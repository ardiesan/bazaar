package com.jadc.bazaar.service;

import java.util.List;

import com.jadc.bazaar.entity.Customer;

public interface CustomerService {
	List<Customer> findAll();
	Customer findById(int id);
	Customer save(Customer customer);
	void deleteById(int id);
}
