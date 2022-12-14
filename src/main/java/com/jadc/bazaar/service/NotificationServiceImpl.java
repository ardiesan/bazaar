package com.jadc.bazaar.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jadc.bazaar.entity.Notification;
import com.jadc.bazaar.repository.NotificationRepository;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {
	private NotificationRepository notificationRepository;

	public NotificationServiceImpl(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

	@Override
	public List<Notification> findAll() {
		return notificationRepository.findAll();
	}

	@Override
	public Page<Notification> findAll(int offset, int entriesPerPage) {
		Pageable pageable = PageRequest.of(offset, entriesPerPage);
		return notificationRepository.findByIsDeletedFalse(pageable);
	}

	@Override
	public Page<Notification> search(String title, int offset, int entriesPerPage) {
		Pageable pageable = PageRequest.of(offset, entriesPerPage);
		return notificationRepository.findByIsDeletedFalseAndTitleContaining(title, pageable);
	}

	@Override
	public void delete(Integer[] ids) {
		Arrays.asList(ids).forEach(this::deleteById);
	}

	@Override
	public void deleteById(int id) {
		Notification notification = notificationRepository.findById(id).orElseThrow();
		notification.setIsDeleted(true);
		notification.setDeletedAt(LocalDateTime.now());

		notificationRepository.save(notification);
	}

	@Override
	public Notification findById(int id) {
		return notificationRepository.findById(id).orElseThrow();
	}

	@Override
	public void save(Notification notification) {
		notificationRepository.save(notification);
	}
}
