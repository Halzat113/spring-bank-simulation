package com.example.service;

import com.example.model.Account;
import com.example.model.Transaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface TransactionService {

    Transaction makeTransaction(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message);
    List<Transaction> findAllTransaction();

    List<Transaction> last10Transactions();
}
