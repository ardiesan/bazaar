package com.jadc.bazaar.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class Customers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(length = 128)
	private String companyName;

	@Column
	private int recordsUsed;

	@Column
	private int contractedRecords;

	@Column
	private boolean isDeleted;

	@Column(updatable = false)
	@CreatedDate
	private LocalDateTime createdAt;

	@Column
	@LastModifiedDate
	private LocalDateTime updatedAt;

	@Column
	private LocalDateTime deletedAt;
}
