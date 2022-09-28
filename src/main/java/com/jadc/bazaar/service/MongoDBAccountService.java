package com.jadc.bazaar.service;

import com.jadc.bazaar.entity.MongoDBAccount;
import com.mongodb.BasicDBObject;

public interface MongoDBAccountService {
	MongoDBAccount save(MongoDBAccount mongoDBAccount);
	MongoDBAccount findById(Integer id);
	public void insertData(Integer id, String username, String password, String url, String dbName);
	BasicDBObject createUserWithRole(String role, String dbName, String username, String password);
	MongoDBAccount createCredentials(Integer id);
}
