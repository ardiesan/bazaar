package com.jadc.bazaar.entity;

import static javax.persistence.DiscriminatorType.STRING;
import static javax.persistence.InheritanceType.SINGLE_TABLE;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Entity
@Table(name = "customers")
@EntityListeners(AuditingEntityListener.class)
@Data
@Inheritance(strategy=SINGLE_TABLE)
@DiscriminatorColumn( name = "type" )
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

	@Column(name = "created_at", updatable = false)
	@CreatedDate
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	@LastModifiedDate
	private LocalDateTime updatedAt;


	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;
}
