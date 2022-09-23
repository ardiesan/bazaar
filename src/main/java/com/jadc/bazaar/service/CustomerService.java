package com.jadc.bazaar.service;

import org.springframework.data.domain.Page;

import com.jadc.bazaar.entity.Customer;

public interface CustomerService {
	Page<Customer> findAll(int offset);
	Page<Customer> search(String companyName, int offset);
	Customer findById(int id);
	Customer save(Customer customer);
	void deleteById(int id);
	void delete(Integer[] ids);
}
