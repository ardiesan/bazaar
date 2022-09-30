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
	@Column(name = "id")
	private int id;

	@Column(name = "account_id")
	private int accountID;

	@Column(name = "db_url")
	private String dbUrl;

	@Column(name = "db_name")
	private String dbName;

	@Column(name = "db_user")
	private String dbUser;

	@Column(name = "db_pass")
	private String dbPass;

}
