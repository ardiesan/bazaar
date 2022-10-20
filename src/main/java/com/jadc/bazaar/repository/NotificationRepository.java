package com.jadc.bazaar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jadc.bazaar.entity.Notifications;

@Repository
public interface NotificationRepository extends JpaRepository<Notifications, Integer> {
	Page<Notifications> findByIsDeletedFalse(Pageable pageable);
	Page<Notifications> findByIsDeletedFalseAndTitleContaining(String title, Pageable pageable);
}
