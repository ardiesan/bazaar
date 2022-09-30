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

import com.jadc.bazaar.entity.AccountDocumentDB;
import com.jadc.bazaar.repository.AccountDocumentDBRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

@Service
public class AccountDocumentDBServiceImpl implements AccountDocumentDBService {
	private final AccountDocumentDBRepository accountDocumentDBRepository;
	private static final Logger logger = LoggerFactory.getLogger(AccountDocumentDBServiceImpl.class);

	@Value("${mongo_account.username}")
	private String ADMIN_USERNAME;

	@Value("${mongo_account.password}")
	String ADMIN_PASSWORD;

	@Value("${mongo_account.authenticate.url}")
	String AUTHENTICATE_URL;

	@Value("${mongo_account.db.url}")
	String DB_URL;

	@Autowired
	public AccountDocumentDBServiceImpl(AccountDocumentDBRepository accountDocumentDBRepository) {
		this.accountDocumentDBRepository = accountDocumentDBRepository;
	}

	@Override
	public AccountDocumentDB save(AccountDocumentDB mongoDBAccount) {
		return accountDocumentDBRepository.save(mongoDBAccount);
	}

	@Override
	public AccountDocumentDB findById(Integer id) {
		return accountDocumentDBRepository.findById(id).get();
	}

	@Override
	public void insertData(Integer id, String username, String password, String url, String dbName) {
		AccountDocumentDB mongoDBAccount = findById(id);
		if (mongoDBAccount != null) {
			mongoDBAccount.setDbName(dbName);
			mongoDBAccount.setDbUrl(url.concat(dbName));
			mongoDBAccount.setDbUser(username);
			mongoDBAccount.setDbPass(password);

			accountDocumentDBRepository.save(mongoDBAccount);
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
	public AccountDocumentDB createCredentials(Integer id) {
		AccountDocumentDB mongoDBAccount = new AccountDocumentDB();
		logger.debug("-Create Credentials-ID value "  + id);
		if(id != 0){
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

			mongoDBAccount.setAccountID(id);
			mongoDBAccount.setDbUser(uuidAsString);
			mongoDBAccount.setDbPass(password);
			mongoDBAccount.setDbName(dbName);
			mongoDBAccount.setDbUrl(dbUrl);
			save(mongoDBAccount);
		}else{
			logger.error("Account not found");
			mongoDBAccount = null;
		}

		return mongoDBAccount;
	}

	@Override
	public void createDatabase(Integer id) {
		String adminRole = "readWrite";
		String userRole = "read";

		AccountDocumentDB mongoDBAccount = createCredentials(id);

		try {
			MongoClient mongo = MongoClients.create(AUTHENTICATE_URL);

			MongoDatabase db = mongo.getDatabase(mongoDBAccount.getDbName());
			db.createCollection("Account Info");

			BasicDBObject admin = createUserWithRole(adminRole, mongoDBAccount.getDbName(), ADMIN_USERNAME, ADMIN_PASSWORD);
			db.runCommand(admin);

			BasicDBObject user = createUserWithRole(userRole, mongoDBAccount.getDbName(), mongoDBAccount.getDbUser(), mongoDBAccount.getDbPass());
			db.runCommand(user);

			insertData(id, mongoDBAccount.getDbUser(), mongoDBAccount.getDbPass(), mongoDBAccount.getDbUrl(), mongoDBAccount.getDbName());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
