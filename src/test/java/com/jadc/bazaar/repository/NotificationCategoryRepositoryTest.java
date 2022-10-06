package com.jadc.bazaar.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.jadc.bazaar.entity.NotificationCategory;


@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class NotificationCategoryRepositoryTest {

	@Autowired
	private NotificationCategoryRepository notificationCategoryRepository;

	@Test
	public void testFindById() {
		int id = 3;
	    NotificationCategory notificationCategory = notificationCategoryRepository.findById(id).get();
		Assertions.assertEquals(id , notificationCategory.getId());
	}

	@Test
	public void testFindAll() {
		int expectedResult = 4;
		List<NotificationCategory> notificationCategories = notificationCategoryRepository.findAll();
		Assertions.assertEquals(expectedResult, notificationCategories.size());
	}

}