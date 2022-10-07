package com.jadc.bazaar.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.jadc.bazaar.entity.NotificationCategory;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NotificationCategoryRepositoryTest {

	@Autowired
	private NotificationCategoryRepository notificationCategoryRepository;

	private NotificationCategory notificationCategory;

	@BeforeEach
	void setup() {
		notificationCategory = new NotificationCategory();
		notificationCategory.setName("Instagram");
		notificationCategory.setIsDeleted(false);
		notificationCategory.setDisplayOrder(12);

		notificationCategoryRepository.save(notificationCategory);
	}

	@Test
	public void testFindById() {
		int id = 3;

		NotificationCategory notificationCategory = notificationCategoryRepository.findById(id).get();
		Assertions.assertEquals(id, notificationCategory.getId());
	}

	@Test
	public void testFindAll() {
		int expectedResult = 5;

		List<NotificationCategory> notificationCategories = notificationCategoryRepository.findAll();
		Assertions.assertEquals(expectedResult, notificationCategories.size());
	}

	@Test
	public void testDeleteById() {
		int expectedResult = 4;

		notificationCategoryRepository.deleteById(notificationCategory.getId());
		Assertions.assertEquals(expectedResult, notificationCategoryRepository.findAll().size());
	}

	@Test
	public void testDeleteAll() {
		notificationCategoryRepository.deleteAll();
		Assertions.assertTrue(notificationCategoryRepository.findAll().isEmpty());
	}

}