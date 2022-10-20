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

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class Notifications {

	@OneToOne(cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "notification_category_id")
	private NotificationCategories notificationCategory;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(length = 128)
	private String title;

	@Column
	@LastModifiedDate
	private LocalDateTime updatedAt;

	@Column(updatable = false)
	@CreatedDate
	private LocalDateTime publicationDate;

	@Column(columnDefinition = "text")
	private String body;

	@Column
	private LocalDateTime deletedAt;

	@Column
	private Boolean isDeleted;
}
