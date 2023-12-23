package com.example.service.impl;

import com.example.dto.AccountDto;
import com.example.entity.Account;
import com.example.enums.AccountStatus;
import com.example.enums.AccountType;
import com.example.mapper.MapperUtil;
import com.example.repository.AccountRepository;
import com.example.service.AccountService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;
    MapperUtil mapperUtil;

    public AccountServiceImpl(AccountRepository accountRepository, MapperUtil mapperUtil) {
        this.accountRepository = accountRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public AccountDto createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId) {
        //we need to create Account object
        AccountDto accountDto = new AccountDto(userId,balance,accountType,creationDate,userId,AccountStatus.ACTIVE);
        //save into the database(repository)
        //return the object created
        accountRepository.save(mapperUtil.convert(accountDto,new Account()));
        return accountDto;
    }

    @Override
    public List<AccountDto> listAllAccount() {
       return accountRepository.findAll().stream()
                .map(account -> mapperUtil.convert(account,new AccountDto())).collect(Collectors.toList());
    }

    @Override
    public List<AccountDto> listAllActiveAccount() {
        return accountRepository.findAllByAccountStatus(AccountStatus.ACTIVE).stream()
                .map(entity -> mapperUtil.convert(entity,new AccountDto()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow();
        account.setAccountStatus(AccountStatus.DELETED);

    }

    @Override
    public void activateAccount(Long id) {
        accountRepository.findById(id).orElseThrow().setAccountStatus(AccountStatus.ACTIVE);
    }

    @Override
    public AccountDto findAccountById(Long id) {
        Account account = accountRepository.findById(id).get();
        return mapperUtil.convert(account,new AccountDto());
    }
}
