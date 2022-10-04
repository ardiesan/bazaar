package com.jadc.bazaar.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.jadc.bazaar.entity.Customer;
import com.jadc.bazaar.exception.UserNotFoundException;
import com.jadc.bazaar.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

	@Mock
	private CustomerRepository customerRepo;
	private CustomerService customerServiceToTest;

	@Captor
	ArgumentCaptor<Customer> customerArgumentCaptor;

	private Customer customer;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		customerServiceToTest = new CustomerServiceImpl(customerRepo);

		customer = new Customer();
		customer.setId(1);
		customer.setCompanyName("Company1");
		customer.setContractedRecords(100);
		customer.setRecordsUsed(0);
		customer.setDeleted(false);
	}

	@Test
	void testFindById() {
		given(customerRepo.findById(customer.getId())).willReturn(Optional.of(customer));

		Customer foundCustomer = customerServiceToTest.findById(customer.getId());

		assertThat(foundCustomer).isNotNull();
	}

	@Test
	void testFindByIdNotFound() {
		given(customerRepo.findById(customer.getId())).willReturn(Optional.ofNullable(null));

		assertThatThrownBy(() -> customerServiceToTest.findById(customer.getId()))
				.isInstanceOf(UserNotFoundException.class)
				.hasMessageContaining("No user id " + customer.getId() + " found");
	}

	@Test
	void testFindAll() {
		int offset = 0;
		int entriesPerPage = 10;
		customerServiceToTest.findAll(offset, entriesPerPage);

		verify(customerRepo).findByIsDeletedFalse(PageRequest.of(offset, entriesPerPage));
	}

	@Test
	void testSearchFoundOneResult() {
		List<Customer> customers = new ArrayList<>();
		customers.add(customer);

		Page<Customer> customerPage = new PageImpl<>(customers);
		given(customerRepo.findByIsDeletedFalseAndCompanyNameContaining(any(), any())).willReturn(customerPage);

		Page<Customer> searchResults = customerServiceToTest.search("Company", 0, 1);
		assertThat(searchResults).isNotNull();
		assertThat(searchResults.getTotalElements()).isEqualTo(1);
	}

	@Test
	void testSearchNoResultsFound() {
		given(customerRepo.findByIsDeletedFalseAndCompanyNameContaining(any(), any())).willReturn(null);

		Page<Customer> searchResults = customerServiceToTest.search("Company2", 0, 1);
		assertThat(searchResults).isNull();
	}

	@Test
	void testSaveCustomer() {
		customerServiceToTest.save(customer);

		verify(customerRepo).save(customerArgumentCaptor.capture());

		Customer capturedCustomer = customerArgumentCaptor.getValue();
		assertThat(capturedCustomer).isEqualTo(customer);
	}

	@Test
	void testDeleteById() {
		given(customerRepo.findById(any())).willReturn(Optional.of(customer));
		customerServiceToTest.deleteById(customer.getId());

		assertThat(customer.isDeleted()).isEqualTo(true);
	}

	@Test
	void testDelete() {
		Integer[] ids = { 1 };
		given(customerRepo.findById(any())).willReturn(Optional.of(customer));
		customerServiceToTest.delete(ids);

		assertThat(customer.isDeleted()).isEqualTo(true);
	}
}