package com.example.service.impl;

import com.example.dto.AccountDto;
import com.example.enums.AccountStatus;
import com.example.enums.AccountType;
import com.example.repository.AccountRepository;
import com.example.service.AccountService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId) {
        //we need to create Account object
        AccountDto accountDto = new AccountDto(userId,balance,accountType,creationDate,userId,AccountStatus.ACTIVE);
        //save into the database(repository)
        //return the object created
        return accountRepository.save(accountDto);
    }

    @Override
    public List<AccountDto> listAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public List<AccountDto> listAllActiveAccount() {
        return accountRepository.findActiveAccounts();
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.findById(id).setAccountStatus(AccountStatus.DELETED);
    }

    @Override
    public void activateAccount(Long id) {
        accountRepository.findById(id).setAccountStatus(AccountStatus.ACTIVE);
    }

    @Override
    public AccountDto findAccountById(Long id) {
        return accountRepository.findById(id);
    }
}
