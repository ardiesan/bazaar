package com.jadc.bazaar.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.jadc.bazaar.entity.Customer;

public interface CustomerService {
	List<Customer> findAll();
	Customer findById(int id);
	Customer save(Customer customer);
	void deleteById(int id);
	void deleteSelectedRows(List<Integer> userIds);
	List<Customer> searchByCompanyNameLike(@Param("keyword") String keyword);
}