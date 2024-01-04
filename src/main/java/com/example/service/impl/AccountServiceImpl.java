package com.example.service.impl;

import com.example.dto.AccountDto;
import com.example.entity.Account;
import com.example.enums.AccountStatus;
import com.example.enums.AccountType;
import com.example.mapper.MapperUtil;
import com.example.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements com.example.service.AccountService {

    AccountRepository accountRepository;
    MapperUtil mapperUtil;

    public AccountServiceImpl(AccountRepository accountRepository, MapperUtil mapperUtil) {
        this.accountRepository = accountRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public AccountDto createNewAccount(AccountDto accountDto) {
        //set creation and account status manually
        accountDto.setCreationDate(new Date());
        accountDto.setAccountStatus(AccountStatus.ACTIVE);
        //save into the database(repository)
        accountRepository.save(mapperUtil.convert(accountDto,new Account()));
        //return the object created
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
        accountRepository.save(account);
    }

    @Override
    public void activateAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow();
        account.setAccountStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);
    }

    @Override
    public AccountDto findAccountById(Long id) {
        return mapperUtil.convert(accountRepository.findById(id).get(),new AccountDto());
    }

    @Override
    public void updateAccount(AccountDto accountDto) {
      accountRepository.save(mapperUtil.convert(accountDto,new Account()));
    }
}
