package com.example.repository;

import com.example.dto.TransactionDto;
import com.example.entity.Account;
import com.example.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    //TODO limit 10 needs to be fixed (having error by doing 'limit by 10')
    @Query("SELECT t from Transaction t order by t.createDate")
    List<Transaction> findLast10Transactions();

    @Query("SELECT t from Transaction t where t.sender='?1' or t.receiver='?1'")
    List<Transaction> findTransactionsById(Long id);
}
