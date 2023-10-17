package com.example.repository;

import com.example.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionRepository extends CrudAbstract<Transaction>{
    public List<Transaction>findLast10Transactions(){
       return findAll().stream()
                       .sorted(Comparator.comparing(Transaction::getCreateDate)
                               .reversed()).collect(Collectors.toList());
    }
}
