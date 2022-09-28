package com.jadc.bazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jadc.bazaar.entity.MongoDBAccount;

public interface MongoDBAccountRepository extends JpaRepository<MongoDBAccount, Integer> {

}
