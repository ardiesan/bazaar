package com.jadc.bazaar.service;

import org.springframework.data.domain.Page;

import com.jadc.bazaar.entity.Customers;

public interface CustomerService {
	Page<Customers> findAll(int offset, int entriesPerPage);
	Page<Customers> search(String companyName, int offset, int entriesPerPage);
	Customers findById(int id);
	Customers save(Customers customer);
	void deleteById(int id);
	void delete(Integer[] ids);
}
