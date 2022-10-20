package com.jadc.bazaar.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.jadc.bazaar.entity.Notifications;

public interface NotificationService {
	List<Notifications> findAll();
	Page<Notifications> findAll(int offset, int entriesPerPage);
	Page<Notifications> search(String companyName, int offset, int entriesPerPage);
	void delete(Integer[] ids);
	void deleteById(int id);
	Notifications findById(int id);
	void save(Notifications notification);
}
