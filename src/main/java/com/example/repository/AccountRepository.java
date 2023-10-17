package com.example.repository;

import com.example.exception.RecordNotFoundException;
import com.example.model.Account;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AccountRepository extends CrudAbstract<Account>{

    public Account findById(UUID id) {
        return super.findAll().stream().
                filter(account -> account.getId().equals(id)).
                findAny().orElseThrow(() -> new RecordNotFoundException("Record not found in the database"));
    }

    public List<Account> findActiveAccounts(){
        return super.findAll().stream().filter(account -> account.getAccountStatus().name().equals("ACTIVE"))
                .collect(Collectors.toList());
    }
}
