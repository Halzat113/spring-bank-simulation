package com.example.service.impl;

import com.example.dto.AccountDto;
import com.example.dto.TransactionDto;
import com.example.entity.Transaction;
import com.example.enums.AccountType;
import com.example.exception.AccountOwnershipException;
import com.example.exception.BadRequestException;
import com.example.exception.BalanceNotSufficientException;
import com.example.exception.UnderConstructionException;
import com.example.mapper.MapperUtil;
import com.example.repository.AccountRepository;
import com.example.repository.TransactionRepository;
import com.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Value("${under_construction}")
    private boolean underConstruction;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final MapperUtil mapperUtil;

    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository, MapperUtil mapperUtil) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public TransactionDto makeTransaction(AccountDto sender, AccountDto receiver, BigDecimal amount, Date creationDate, String message) {
        if (!underConstruction) {
            validateAccount(sender, receiver);
            checkAccountOwnership(sender, receiver);
            executeBalanceAndUpdateIfRequired(amount, sender, receiver);

            TransactionDto transactionDto = new TransactionDto(sender,receiver,amount,message,creationDate);

            transactionRepository.save(mapperUtil.convert(transactionDto,new Transaction()));
            return transactionDto;
        }else {
            throw new UnderConstructionException("App is under construction, please try again later.");
        }
    }

    private void validateAccount(AccountDto sender, AccountDto receiver) {
        /*
            -if any of the account is null
            -if account ids are the same(same account)
            -if the account exist in the database(repository)
         */
        if(sender==null||receiver==null){
            throw new BadRequestException("sender or receiver cannot be null");
        }
        if (sender.getId().equals(receiver.getId())){
            throw new BadRequestException("sender and receiver's id cannot be the same");
        }

        findAccountById(sender.getId());
        findAccountById(receiver.getId());




    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, AccountDto sender, AccountDto receiver) {
        if(checkSenderBalance(sender,amount)){
            //update sender and receiver balance
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));
        }else {
            throw new BalanceNotSufficientException("Balance is not enough for this transaction");
        }
    }

    private boolean checkSenderBalance(AccountDto sender, BigDecimal amount) {
        return sender.getBalance().compareTo(amount)>=0;
    }

    public void checkAccountOwnership(AccountDto sender, AccountDto receiver){
        /*
        write an if statement that checks if one of the account is saving,
        and user of sender or receiver is not the same, throw AccountOwnershipException
         */

        if (sender.getAccountType().equals(AccountType.SAVING)||receiver.getAccountType().equals(AccountType.SAVING)){
            if (!sender.getUserId().equals(receiver.getUserId())){
                throw new AccountOwnershipException("user must be the same for sender and receiver");
            }
        }
    }

    private void findAccountById(Long id){
      accountRepository.findById(id);
    }

    @Override
    public List<TransactionDto> findAllTransaction() {
       return transactionRepository.findAll().stream()
               .map(transaction -> mapperUtil.convert(transaction,new TransactionDto()))
               .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> last10Transactions() {
        return transactionRepository.findLast10Transactions().stream()
                .map(transaction ->mapperUtil.convert(transaction,new TransactionDto()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> findTransactionListById(Long id) {
        return transactionRepository.findTransactionsById(id).stream()
                .map(transaction -> mapperUtil.convert(transaction,new TransactionDto()))
                .collect(Collectors.toList());
    }
}
