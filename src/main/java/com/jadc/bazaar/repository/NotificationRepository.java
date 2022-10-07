package com.jadc.bazaar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jadc.bazaar.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
	Page<Notification> findByIsDeletedFalse(Pageable pageable);
	Page<Notification> findByIsDeletedFalseAndTitleContaining(String title, Pageable pageable);
}
