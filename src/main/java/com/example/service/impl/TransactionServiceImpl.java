package com.example.service.impl;

import com.example.enums.AccountType;
import com.example.exception.AccountOwnershipException;
import com.example.exception.BadRequestException;
import com.example.exception.BalanceNotSufficientException;
import com.example.exception.UnderConstructionException;
import com.example.model.Account;
import com.example.model.Transaction;
import com.example.repository.AccountRepository;
import com.example.repository.TransactionRepository;
import com.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionServiceImpl implements TransactionService {
    @Value("${under_construction}")
    private boolean underConstruction;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository){
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }
    @Override
    public Transaction makeTransaction(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message) {
        if (!underConstruction) {
            validateAccount(sender, receiver);
            checkAccountOwnership(sender, receiver);
            executeBalanceAndUpdateIfRequired(amount, sender, receiver);

            Transaction transaction = Transaction.builder().sender(UUID.randomUUID()).receiver(UUID.randomUUID())
                    .amount(amount).createDate(creationDate).message(message).build();

            return transactionRepository.save(transaction);
        }else {
            throw new UnderConstructionException("App is under construction, please try again later.");
        }
    }

    private void validateAccount(Account sender,Account receiver) {
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

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, Account sender,Account receiver) {
        if(checkSenderBalance(sender,amount)){
            //update sender and receiver balance
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));
        }else {
            throw new BalanceNotSufficientException("Balance is not enough for this transaction");
        }
    }

    private boolean checkSenderBalance(Account sender, BigDecimal amount) {
        return sender.getBalance().compareTo(amount)>=0;
    }

    public void checkAccountOwnership(Account sender,Account receiver){
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

    private void findAccountById(UUID id){
      accountRepository.findById(id);
    }

    @Override
    public List<Transaction> findAllTransaction() {
       return transactionRepository.findAll();
    }
}
