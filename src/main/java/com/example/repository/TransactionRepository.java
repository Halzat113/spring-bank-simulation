package com.example.repository;
import com.example.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    @Query(value = "select * from transactions order by create_date limit 10;",nativeQuery = true)
    List<Transaction> findLast10Transactions();

    @Query("SELECT t from Transaction t where t.sender=?1 or t.receiver=?1")
    List<Transaction> findTransactionsById(Long id);
}
