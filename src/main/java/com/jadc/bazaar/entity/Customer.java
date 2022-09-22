package com.jadc.bazaar.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Entity
@Table(name = "customers")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "company_name", length = 128)
	private String companyName;

	@Column(name = "records_used")
	private int recordsUsed;

	@Column(name = "contracted_records")
	private int contractedRecords;

	@Column(name = "is_deleted")
	private boolean isDeleted;

	@Column(name = "created_at", updatable = false, length = 32)
	@CreatedDate
	private String createdAt;

	@Column(name = "updated_at", length = 32)
	@LastModifiedDate
	private String updatedAt;

	@Column(name = "deleted_at", length = 32)
	private String deletedAt;
}
