package com.jadc.bazaar.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Entity
@Table(name = "notifications")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Notification {

	//@OneToOne(cascade = CascadeType.ALL)
	@OneToOne(cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "notification_category_id")
	private NotificationCategory notificationCategory;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "title", length = 128)
	private String title;

	@Column(name = "updated_at")
	@LastModifiedDate
	private LocalDateTime updatedAt;

	@Column(name = "created_at", updatable = false)
	@CreatedDate
	private LocalDateTime publicationDate;

	@Column(name = "body", columnDefinition = "text")
	private String body;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Column(name = "is_deleted")
	private Boolean isDeleted;
}
