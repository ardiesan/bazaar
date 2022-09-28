package com.jadc.bazaar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jadc.bazaar.entity.MongoDBAccount;
import com.jadc.bazaar.service.MongoDBAccountService;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

@RestController
@RequestMapping("/customer")
public class MongoDBAccountController {

	private final MongoDBAccountService mongoDBAccountService;
	private static final Logger logger = LoggerFactory.getLogger(MongoDBAccountController.class);

	@Value("${mongo_account.username}")
	private String ADMIN_USERNAME;

	@Value("${mongo_account.password}")
	String ADMIN_PASSWORD;

	@Value("${mongo_account.authenticate.url}")
	String AUTHENTICATE_URL;

	@Autowired
	public MongoDBAccountController(MongoDBAccountService service) {
		this.mongoDBAccountService = service;
	}

	@PutMapping("/create_db/{id}")
	public void createDatabase(@PathVariable Integer id) {
		String adminRole = "readWrite";
		String userRole = "read";

		MongoDBAccount mongoDBAccount = mongoDBAccountService.createCredentials(id);

		try {
			MongoClient mongo = MongoClients.create(AUTHENTICATE_URL);

			MongoDatabase db = mongo.getDatabase(mongoDBAccount.getDbName());
			db.createCollection("Account Info");

			BasicDBObject admin = mongoDBAccountService.createUserWithRole(adminRole, mongoDBAccount.getDbName(), ADMIN_USERNAME, ADMIN_PASSWORD);
			db.runCommand(admin);

			BasicDBObject user = mongoDBAccountService.createUserWithRole(userRole, mongoDBAccount.getDbName(), mongoDBAccount.getDbUser(), mongoDBAccount.getDbPass());
			db.runCommand(user);

			mongoDBAccountService.insertData(id, mongoDBAccount.getDbUser(), mongoDBAccount.getDbPass(), mongoDBAccount.getDbUrl(), mongoDBAccount.getDbName());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}
