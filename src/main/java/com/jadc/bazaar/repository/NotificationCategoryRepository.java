package com.jadc.bazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jadc.bazaar.entity.NotificationCategories;

@Repository
public interface NotificationCategoryRepository extends JpaRepository<NotificationCategories, Integer> {
}
