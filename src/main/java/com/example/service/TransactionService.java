package com.example.service;

import com.example.dto.AccountDto;
import com.example.dto.TransactionDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TransactionService {

    TransactionDto makeTransaction(AccountDto sender, AccountDto receiver, BigDecimal amount, Date creationDate, String message);
    List<TransactionDto> findAllTransaction();

    List<TransactionDto> last10Transactions();

    List<TransactionDto> findTransactionListById(Long id);
}
