package com.jadc.bazaar.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.jadc.bazaar.entity.Notification;

public interface NotificationService {
	List<Notification> findAll();
	Page<Notification> findAll(int offset, int entriesPerPage);
	Page<Notification> search(String companyName, int offset, int entriesPerPage);
	void delete(Integer[] ids);
	void deleteById(int id);
	Notification findById(int id);
}
