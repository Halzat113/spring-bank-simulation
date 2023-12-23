package com.example.repository;

import com.example.dto.TransactionDto;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TransactionRepository extends CrudAbstract<TransactionDto>{

    public List<TransactionDto>findLast10Transactions(){
       return findAll().stream()
                       .sorted(Comparator.comparing(TransactionDto::getCreateDate)
                               .reversed()).limit(10).collect(Collectors.toList());
    }

    public List<TransactionDto> findTransactionsById(UUID id){
        return findAll().stream().filter(t->t.getSender().equals(id)||t.getReceiver().equals(id)).collect(Collectors.toList());
    }
}
