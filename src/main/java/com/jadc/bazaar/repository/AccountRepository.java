package com.jadc.bazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jadc.bazaar.entity.Account;
import com.jadc.bazaar.entity.Customer;

public interface AccountRepository extends JpaRepository<Account, Integer> {

}
