package com.jadc.bazaar.service;

import org.springframework.data.domain.Page;

import com.jadc.bazaar.entity.Customer;

public interface CustomerService {
	Page<Customer> findAll(int offset, int entriesPerPage);
	Page<Customer> search(String companyName, int offset, int entriesPerPage);
	Customer findById(int id);
	Customer save(Customer customer);
	void deleteById(int id);
	void delete(Integer[] ids);
}
