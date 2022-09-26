package com.jadc.bazaar.service;

import com.jadc.bazaar.entity.Account;
import com.jadc.bazaar.entity.Customer;
import com.mongodb.BasicDBObject;

public interface AccountService {
	Account save(Account account);
	Account findById(Integer id);
	public void insertData(Integer id, String username, String password, String url, String dbName);
	BasicDBObject createUserWithRole(String role, String dbName, String username, String password);
	Account createCredentials(Integer id);
}
