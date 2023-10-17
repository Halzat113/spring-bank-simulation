package com.example.repository;

import com.example.exception.RecordNotFoundException;
import com.example.model.Account;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class AccountRepository extends CrudAbstract<Account>{

    public Account findById(UUID id) {
        return findAll().stream().
                filter(account -> account.getId().equals(id)).
                findAny().orElseThrow(() -> new RecordNotFoundException("Record not found in the database"));
    }
}
