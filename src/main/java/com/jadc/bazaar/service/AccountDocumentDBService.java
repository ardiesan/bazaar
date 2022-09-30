package com.jadc.bazaar.service;

import com.jadc.bazaar.entity.AccountDocumentDB;
import com.mongodb.BasicDBObject;

public interface AccountDocumentDBService {
	AccountDocumentDB save(AccountDocumentDB mongoDBAccount);
	AccountDocumentDB findById(Integer id);
	public void insertData(Integer id, String username, String password, String url, String dbName);
	BasicDBObject createUserWithRole(String role, String dbName, String username, String password);
	AccountDocumentDB createCredentials(Integer id);
	public void createDatabase(Integer id);
}
