package com.jadc.bazaar.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jadc.bazaar.entity.Customer;
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
	public Customer findById(int id) {
		Optional<Customer> result = customerRepository.findById(id);
		Customer customer;

		if(result.isPresent()) {
			customer = result.get();
		}
		else {
			throw new UserNotFoundException("User with id: " + id + " not found.");
		}

		return customer;
	}

	@Override
	public List<Customer> findAll() {
		return customerRepository.findByIsDeletedFalse();
	}

	@Override
	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public void deleteById(int id) {
		Optional<Customer> result = customerRepository.findById(id);
		Customer customer;

		if(result.isPresent()) {
			customer = result.get();
		}
		else {
			throw new UserNotFoundException("User with id: " + id + " not found.");
		}

		customer.setDeleted(true);
		customer.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		customer.setDeletedAt(new Timestamp(System.currentTimeMillis()));
	}

	@Override
	public void deleteSelectedRows(List<Integer> userIds) {
		customerRepository.deleteSelectedRows(userIds);
	}

	@Override
	public List<Customer> searchByCompanyNameLike(String keyword) {
		return customerRepository.searchByCompanyNameLike(keyword);
	}
}
