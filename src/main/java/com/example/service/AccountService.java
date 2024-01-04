package com.example.service;

import com.example.dto.AccountDto;
import com.example.enums.AccountType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AccountService {
    AccountDto createNewAccount(AccountDto accountDto);
    List<AccountDto> listAllAccount();

    List<AccountDto> listAllActiveAccount();

    void deleteAccount(Long id);

    void activateAccount(Long id);

    AccountDto findAccountById(Long id);

    void updateAccount(AccountDto accountDto);
}
