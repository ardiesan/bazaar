package com.jadc.bazaar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jadc.bazaar.entity.Customers;

public interface CustomerRepository extends JpaRepository<Customers, Integer> {
	Page<Customers> findByIsDeletedFalse(Pageable pageable);
	Page<Customers> findByIsDeletedFalseAndCompanyNameContaining(String companyName, Pageable pageable);
}
