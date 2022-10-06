package com.jadc.bazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jadc.bazaar.entity.NotificationCategory;

public interface NotificationCategoryRepository extends JpaRepository<NotificationCategory, Integer> {

}
