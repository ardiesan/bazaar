package com.jadc.bazaar.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "customers")
@NoArgsConstructor
@DiscriminatorValue(value = "A")
public class Account extends Customer{

	@Column(name = "db_url")
	private String dbUrl;

	@Column(name = "db_name")
	private String dbName;

	@Column(name = "db_user")
	private String dbUser;

	@Column(name = "db_pass")
	private String dbPass;

}
