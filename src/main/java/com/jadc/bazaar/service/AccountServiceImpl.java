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

import com.jadc.bazaar.entity.Account;
import com.jadc.bazaar.exception.UserNotFoundException;
import com.jadc.bazaar.repository.AccountRepository;
import com.mongodb.BasicDBObject;

@Service
public class AccountServiceImpl implements AccountService {
	private final AccountRepository accountRepository;
	final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Value("${mongodb.url}")
	String DB_URL;

	@Autowired
	public AccountServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public Account save(Account account) {
		return accountRepository.save(account);
	}

	@Override
	public Account findById(Integer id) {
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("No user id " + id + " found"));

		return account;
	}

	@Override
	public void insertData(Integer id, String username, String password, String url, String dbName) {
		Account account = accountRepository.findById(id).get();
		if(account != null){
			account.setDbName(dbName);
			account.setDbUrl(url.concat(dbName));
			account.setDbUser(username);
			account.setDbPass(password);
			account.setUpdatedAt(LocalDateTime.now());

			accountRepository.save(account);
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
	public Account createCredentials(Integer id) {
		Account findAccount = accountRepository.findById(id).get();
		Account account = new Account();

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
//		System.out.println("Encrypted Password:"+encodedPassword);
		String dbName = uuidAsString;
		String dbUrl = DB_URL.concat(dbName);

		account.setDbUser(uuidAsString);
		account.setDbPass(password);
		account.setDbName(dbName);
		account.setDbUrl(dbUrl);

		return account;
	}
}
