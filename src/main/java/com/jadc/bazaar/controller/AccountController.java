package com.jadc.bazaar.controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jadc.bazaar.entity.Account;
import com.jadc.bazaar.service.AccountService;
import com.jadc.bazaar.service.CustomerService;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
@RestController
@RequestMapping("/customer")
public class AccountController {

	private final AccountService accountService;

	@Value("${admin.username}")
	private  String ADMIN_USERNAME;

	@Value("${admin.password}")
	String  ADMIN_PASSWORD;

	@Autowired
	public AccountController(AccountService service) {
		this.accountService = service;
	}


	@PutMapping("/create_db/{id}")
	public void createDatabase(@PathVariable Integer id){
		String adminRole = "readWrite";
		String userRole = "read";

		Account account = accountService.createCredentials(id);

		try{
			MongoClient mongo = MongoClients.create("mongodb://rootuser:rootpass@localhost:27017/?authSource=admin");

			MongoDatabase db = mongo.getDatabase(account.getDbName());
			db.createCollection("Account Info");

			BasicDBObject admin = accountService.createUserWithRole(adminRole, account.getDbName(), ADMIN_USERNAME, ADMIN_PASSWORD);
			db.runCommand(admin);

			BasicDBObject user = accountService.createUserWithRole(userRole, account.getDbName(), account.getDbUser(), account.getDbPass());
			db.runCommand(user);

			accountService.insertData(id, account.getDbUser(), account.getDbPass(), account.getDbUrl(), account.getDbName());
		}catch (Exception e){
			e.printStackTrace();
		}

	}

}
