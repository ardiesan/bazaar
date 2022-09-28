package com.jadc.bazaar.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jadc.bazaar.entity.MongoDBAccount;
import com.jadc.bazaar.exception.UserNotFoundException;
import com.jadc.bazaar.repository.MongoDBAccountRepository;
import com.mongodb.BasicDBObject;

@Service
public class MongoDBAccountServiceImpl implements MongoDBAccountService {
	private final MongoDBAccountRepository mongoDBAccountRepository;
	private static final Logger logger = LoggerFactory.getLogger(MongoDBAccountServiceImpl.class);

	@Value("${mongo_account.db.url}")
	String DB_URL;

	@Autowired
	public MongoDBAccountServiceImpl(MongoDBAccountRepository mongoDBAccountRepository) {
		this.mongoDBAccountRepository = mongoDBAccountRepository;
	}

	@Override
	public MongoDBAccount save(MongoDBAccount mongoDBAccount) {
		return mongoDBAccountRepository.save(mongoDBAccount);
	}

	@Override
	public MongoDBAccount findById(Integer id) {
		MongoDBAccount mongoDBAccount = mongoDBAccountRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("No user id " + id + " found"));

		return mongoDBAccount;
	}

	@Override
	public void insertData(Integer id, String username, String password, String url, String dbName) {
		MongoDBAccount mongoDBAccount = mongoDBAccountRepository.findById(id).get();
		if (mongoDBAccount != null) {
			mongoDBAccount.setDbName(dbName);
			mongoDBAccount.setDbUrl(url.concat(dbName));
			mongoDBAccount.setDbUser(username);
			mongoDBAccount.setDbPass(password);
			mongoDBAccount.setUpdatedAt(LocalDateTime.now());

			mongoDBAccountRepository.save(mongoDBAccount);
		}
	}

	@Override
	public BasicDBObject createUserWithRole(String role, String dbName, String username, String password) {
		BasicDBObject user = new BasicDBObject("createUser", username)
				.append("pwd", password)
				.append("roles",
						Collections.singletonList(
								new BasicDBObject(
										"role", role).append("db", dbName)
						));
		return user;
	}

	@Override
	public MongoDBAccount createCredentials(Integer id) {
		MongoDBAccount findMongoDBAccount = mongoDBAccountRepository.findById(id).get();
		MongoDBAccount mongoDBAccount = new MongoDBAccount();

		// Generate uuid for username and dbname
		UUID uuid = UUID.randomUUID();
		String uuidAsString = uuid.toString();

		// Generate random password and encrypt
		CharacterRule digits = new CharacterRule(EnglishCharacterData.Digit);
		CharacterRule lowerCase = new CharacterRule(EnglishCharacterData.LowerCase);
		CharacterRule upperCase = new CharacterRule(EnglishCharacterData.UpperCase);

		PasswordGenerator passwordGenerator = new PasswordGenerator();
		String password = passwordGenerator.generatePassword(8, digits, lowerCase, upperCase);
		logger.debug("Printing password value: " + password);

		//		String encodedPassword = new BCryptPasswordEncoder().encode(password);
		//		logger.debug("Printing encoded password value: " + encodedPassword);
		String dbName = uuidAsString;
		String dbUrl = DB_URL.concat(dbName);

		mongoDBAccount.setDbUser(uuidAsString);
		mongoDBAccount.setDbPass(password);
		mongoDBAccount.setDbName(dbName);
		mongoDBAccount.setDbUrl(dbUrl);

		return mongoDBAccount;
	}
}
