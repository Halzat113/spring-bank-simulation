package com.example.repository;

import com.example.dto.AccountDto;
import com.example.exception.RecordNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountRepository extends CrudAbstract<AccountDto>{

    public AccountDto findById(Long id) {
        return super.findAll().stream().
                filter(account -> account.getId().equals(id)).
                findAny().orElseThrow(() -> new RecordNotFoundException("Record not found in the database"));
    }

    public List<AccountDto> findActiveAccounts(){
        return super.findAll().stream().filter(account -> account.getAccountStatus().name().equals("ACTIVE"))
                .collect(Collectors.toList());
    }
}
