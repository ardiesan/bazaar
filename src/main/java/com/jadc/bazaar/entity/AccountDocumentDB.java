package com.jadc.bazaar.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table
@NoArgsConstructor
public class AccountDocumentDB {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private int accountID;

	@Column
	private String dbUrl;

	@Column
	private String dbName;

	@Column
	private String dbUser;

	@Column
	private String dbPass;

}
