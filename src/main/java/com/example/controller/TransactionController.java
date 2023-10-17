package com.example.controller;

import com.example.model.Account;
import com.example.model.Transaction;
import com.example.service.AccountService;
import com.example.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class TransactionController {
    TransactionService transactionService;
    AccountService accountService;

    public TransactionController(TransactionService transactionService,AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @GetMapping("/make-transfer")
    public String getMakeTransfer(Model model){
        model.addAttribute("transaction", Transaction.builder().build());
        model.addAttribute("accounts",accountService.listAllActiveAccount());
        model.addAttribute("lastTransactions",transactionService.last10Transactions());
        return "transaction/make-transfer";
    }

    @PostMapping("/make-transaction")
    public String makeTransaction(@ModelAttribute("transaction")Transaction transaction){
        Account sender = accountService.findAccountById(transaction.getSender());
        Account receiver = accountService.findAccountById(transaction.getReceiver());
        transactionService.makeTransaction(sender,receiver,transaction.getAmount(),new Date(),transaction.getMessage());
        return "redirect:/make-transfer";
    }
}
