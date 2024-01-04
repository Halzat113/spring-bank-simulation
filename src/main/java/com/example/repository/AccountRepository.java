package com.example.repository;

import com.example.entity.Account;
import com.example.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account,Long> {
    List<Account> findAllByAccountStatus(AccountStatus status);
}
