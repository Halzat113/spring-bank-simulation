package com.example.repository;

import com.example.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TransactionRepository extends CrudAbstract<Transaction>{

    public List<Transaction>findLast10Transactions(){
       return findAll().stream()
                       .sorted(Comparator.comparing(Transaction::getCreateDate)
                               .reversed()).limit(10).collect(Collectors.toList());
    }

    public List<Transaction> findTransactionsById(UUID id){
        return findAll().stream().filter(t->t.getSender().equals(id)||t.getReceiver().equals(id)).collect(Collectors.toList());
    }
}
