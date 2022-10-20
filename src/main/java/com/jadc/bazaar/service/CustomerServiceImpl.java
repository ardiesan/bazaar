package com.jadc.bazaar.service;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jadc.bazaar.entity.Customers;
import com.jadc.bazaar.exception.UserNotFoundException;
import com.jadc.bazaar.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;

	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public Customers findById(int id) {
		return customerRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("No user id " + id + " found"));
	}

	@Override
	public Page<Customers> findAll(int offset, int entriesPerPage) {
		Pageable pageable = PageRequest.of(offset, entriesPerPage);

		return customerRepository.findByIsDeletedFalse(pageable);
	}

	@Override
	public Page<Customers> search(String companyName, int offset, int entriesPerPage) {
		Pageable pageable = PageRequest.of(offset, entriesPerPage);

		return customerRepository.findByIsDeletedFalseAndCompanyNameContaining(companyName, pageable);
	}

	@Override
	public Customers save(Customers customer) {
		return customerRepository.save(customer);
	}

	@Override
	public void deleteById(int id) {
		Customers customer = this.findById(id);
		customer.setDeleted(true);

		customer.setDeletedAt(LocalDateTime.now());

		customerRepository.save(customer);
	}

	@Override
	public void delete(Integer[] ids) {
		Arrays.asList(ids).forEach(this::deleteById);
	}
}
