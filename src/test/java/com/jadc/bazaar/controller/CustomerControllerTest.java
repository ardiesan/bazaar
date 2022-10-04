package com.jadc.bazaar.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.jadc.bazaar.entity.Customer;
import com.jadc.bazaar.service.CustomerService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CustomerController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CustomerControllerTest {

	private static final String URL_LOCALE = "/en";
	private static final String URL_PREFIX = "/admin/account/";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerService customerService;

	@InjectMocks
	private CustomerController customerController;

	Customer customer;
	List<Customer> customers;

	@BeforeEach
	void setUp() {
		customer = new Customer();
		customer.setId(1);
		customer.setCompanyName("Company1");
		customer.setContractedRecords(100);
		customer.setRecordsUsed(0);
		customer.setDeleted(false);

		customers = Arrays.asList(customer);
	}

	@Test
	void testListAll() throws Exception {
		Page<Customer> customerPage = new PageImpl<>(customers);

		when(customerService.findAll(0, 3)).thenReturn(customerPage);
		mockMvc.perform(get(URL_LOCALE + URL_PREFIX))
				.andExpect(status().isOk())
				.andExpect(view().name("customers"))
				.andReturn();
	}

	@Test
	void testListAllWithPagination() throws Exception {
		int pageNumber = 2;
		int[] pageNumbers = {1, 2};
		String url = URL_LOCALE + URL_PREFIX + "/{pageNumber}";
		mockMvc.perform(get(url, pageNumber))
				.andExpect(view().name("customers"))
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	void testShowCustomerFormForCreateCustomer() throws Exception {
		String url = URL_LOCALE + URL_PREFIX + "/add";

		mockMvc.perform(get(url))
				.andExpect(view().name("customer-form"))
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	void testShowCustomerFormForUpdateCustomer() throws Exception {
		int id = 1;
		String url = URL_LOCALE + URL_PREFIX + "/update/{id}";

		given(customerService.findById(id)).willReturn(customer);

		mockMvc.perform(get(url, 1))
				.andExpect(view().name("customer-form"))
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	void testShowCustomerFormForUpdateCustomerWithNoPathVariable() throws Exception {
		int id = 1;
		String url = URL_LOCALE + URL_PREFIX + "/update";

		given(customerService.findById(id)).willReturn(null);

		mockMvc.perform(get(url))
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	void testSaveCustomer() throws Exception {
		String url = URL_LOCALE + URL_PREFIX + "/save";
		String companyName = "Company1";
		int contractedRecords = 1000;

		MvcResult mvcResult = mockMvc.perform(post(url).param("id", String.valueOf(1))
						.param("companyName", companyName)
						.param("contractedRecords", String.valueOf(contractedRecords)))
				.andExpect(status().is3xxRedirection())
				.andReturn();
	}

	@Test
	void testDeleteCustomers() throws Exception {
		String url = URL_LOCALE + URL_PREFIX + "/delete";
		willDoNothing().given(customerService).delete(any());

		mockMvc.perform(post(url).param("customers", "1"))
				.andExpect(status().is3xxRedirection())
				.andReturn();
	}

	@Test
	void testDeleteCustomersWithNoParam() throws Exception {
		String url = URL_LOCALE + URL_PREFIX + "/delete";
		willDoNothing().given(customerService).delete(any());

		mockMvc.perform(post(url))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
}