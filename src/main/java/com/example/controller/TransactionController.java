package com.example.controller;

import com.example.dto.AccountDto;
import com.example.dto.TransactionDto;
import com.example.service.AccountService;
import com.example.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

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
        model.addAttribute("transaction", new TransactionDto());
        model.addAttribute("accounts",accountService.listAllActiveAccount());
        model.addAttribute("lastTransactions",transactionService.last10Transactions());
        return "transaction/make-transfer";
    }

    @PostMapping("/make-transaction")
    public String makeTransaction(@Valid @ModelAttribute("transaction") TransactionDto transactionDto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("accounts",accountService.listAllAccount());
            model.addAttribute("lastTransactions",transactionService.last10Transactions());
            return "transaction/make-transfer";
        }
        AccountDto sender = accountService.findAccountById(transactionDto.getSender().getId());
        AccountDto receiver = accountService.findAccountById(transactionDto.getReceiver().getId());
        transactionService.makeTransaction(sender,receiver, transactionDto.getAmount(),new Date(), transactionDto.getMessage());
        return "redirect:/make-transfer";
    }

    @GetMapping("/transaction/{id}")
    public String transactions(@PathVariable("id") Long id, Model model){
        System.out.println(id);
        //get the list od transactions based on id and return as a model attribute
        //TASK- complete the method(service and repository)
        //findTransactionListById
        model.addAttribute("transactions",transactionService.findTransactionListById(id));
        return "/transaction/transactions";
    }
}
