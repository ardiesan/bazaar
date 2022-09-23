package com.jadc.bazaar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jadc.bazaar.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	Page<Customer> findByIsDeletedFalse(Pageable pageable);
	Page<Customer> findByIsDeletedFalseAndCompanyNameContaining(String companyName, Pageable pageable);
}
