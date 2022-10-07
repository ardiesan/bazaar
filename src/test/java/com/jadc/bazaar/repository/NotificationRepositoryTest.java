package com.jadc.bazaar.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jadc.bazaar.entity.Notification;
import com.jadc.bazaar.entity.NotificationCategory;

@SpringBootTest
class NotificationRepositoryTest {

	@Autowired NotificationRepository notificationRepository;

	@Autowired NotificationCategoryRepository notificationCategoryRepository;

	@Test
	void testInsertNotification() {
		NotificationCategory category = notificationCategoryRepository.findById(3).orElseThrow();
		assertThat(category.getName()).isEqualTo("twitter");

		Notification notification = new Notification();
		notification.setId(1);
		notification.setTitle("Twitter Notification");
		notification.setNotificationCategory(category);
		notification.setBody("You have new notification from twitter!");
		notification.setPublicationDate(LocalDateTime.now());
		notification.setUpdatedAt(LocalDateTime.now());

		notificationRepository.save(notification);

		Notification getNotification = notificationRepository.findById(1).orElseThrow();
		assertThat(getNotification.getTitle()).isEqualTo("Twitter Notification");
	}

	@Test
	void testInsertManyNotifications() {
		final NotificationCategory category = notificationCategoryRepository.findById(3).orElseThrow();

		IntStream.range(0, 20).forEach(number -> {
			Notification notification = new Notification();
			notification.setId(number);
			notification.setTitle("Notification "+number);
			notification.setNotificationCategory(category);
			notification.setBody("You have new notification "+number);
			notification.setPublicationDate(LocalDateTime.now());
			notification.setUpdatedAt(LocalDateTime.now());
			notification.setIsDeleted(false);

			notificationRepository.save(notification);
		});

		final NotificationCategory category2 = notificationCategoryRepository.findById(2).orElseThrow();

		IntStream.range(21, 41).forEach(number -> {
			Notification notification = new Notification();
			notification.setId(number);
			notification.setTitle("Notification "+number);
			notification.setNotificationCategory(category2);
			notification.setBody("You have new notification "+number);
			notification.setPublicationDate(LocalDateTime.now());
			notification.setUpdatedAt(LocalDateTime.now());
			notification.setIsDeleted(false);

			notificationRepository.save(notification);
		});

		final NotificationCategory category3 = notificationCategoryRepository.findById(4).orElseThrow();

		IntStream.range(21, 41).forEach(number -> {
			Notification notification = new Notification();
			notification.setId(number);
			notification.setTitle("Notification "+number);
			notification.setNotificationCategory(category3);
			notification.setBody("You have new notification "+number);
			notification.setPublicationDate(LocalDateTime.now());
			notification.setUpdatedAt(LocalDateTime.now());
			notification.setIsDeleted(false);

			notificationRepository.save(notification);
		});

		final NotificationCategory category4 = notificationCategoryRepository.findById(1).orElseThrow();

		IntStream.range(42, 62).forEach(number -> {
			Notification notification = new Notification();
			notification.setId(number);
			notification.setTitle("Notification "+number);
			notification.setNotificationCategory(category4);
			notification.setBody("You have new notification "+number);
			notification.setPublicationDate(LocalDateTime.now());
			notification.setUpdatedAt(LocalDateTime.now());
			notification.setIsDeleted(false);

			notificationRepository.save(notification);
		});
	}

	@Test
	void testDeleteAllNotification() {
		notificationRepository.deleteAll();
		assertThat(notificationRepository.findAll().size()).isZero();
	}

	@Test
	void testInitializeNotificationCategories() {

	}

	@Test
	void testGetNotificationCategories() {

		List<NotificationCategory> list = notificationCategoryRepository.findAll();
		System.out.println(list.size());

		assertThat(list).size().isEqualTo(4);

		NotificationCategory notificationCategory = notificationCategoryRepository.findById(7).orElseThrow();
		System.out.println(notificationCategory);

		//Assertions.assertThat(notificationCategory.getName()).isEqualTo("line");
	}

	@Test
	void testDeleteAllNotificationCategories() {
		notificationCategoryRepository.deleteAll();
		assertThat(notificationCategoryRepository.findAll().size()).isZero();
	}
}