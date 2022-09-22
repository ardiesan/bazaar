package com.jadc.bazaar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jadc.bazaar.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	List<Customer> findByIsDeletedFalse();

	@Query(nativeQuery = true, value = "SELECT * FROM customers WHERE company_name LIKE %:keyword%")
	List<Customer> searchByCompanyNameLike(@Param("keyword") String keyword);
}