package com.example;

import com.example.enums.AccountType;
import com.example.model.Account;
import com.example.service.AccountService;
import com.example.service.TransactionService;
import com.example.service.impl.TransactionServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootApplication
public class BankSimulationAppApplication {

    public static void main(String[] args) {
        ApplicationContext container = SpringApplication.run(BankSimulationAppApplication.class, args);

        //get account and transaction service beans
        AccountService accountService = container.getBean(AccountService.class);
        TransactionService transactionService = container.getBean(TransactionService.class);

        Account sender = accountService.createNewAccount(BigDecimal.valueOf(70),new Date(), AccountType.CHECKING,1L);
        Account receiver = accountService.createNewAccount(BigDecimal.valueOf(50),new Date(), AccountType.SAVING,2L);
        Account receiver2 = null;

        accountService.listAllAccount().forEach(System.out::println);

        transactionService.makeTransaction(sender,receiver,new BigDecimal(40),new Date(),"Transaction 1" );

        System.out.println(transactionService.findAllTransaction().get(0));

        accountService.listAllAccount().forEach(System.out::println);



    }

}
