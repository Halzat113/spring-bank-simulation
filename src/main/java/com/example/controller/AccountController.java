package com.example.controller;

import com.example.enums.AccountType;
import com.example.model.Account;
import com.example.repository.AccountRepository;
import com.example.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.Date;

@Controller
public class AccountController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;

    public AccountController(AccountService accountService,AccountRepository accountRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @GetMapping(value={"/index","/"})
    public String getIndexPage (Model model){
        model.addAttribute("accountList",accountService.listAllAccount());
        return "account/index";
    }

    @GetMapping("/create")
    public String createAccount(Model model){
        model.addAttribute("account",Account.builder().build());
        model.addAttribute("accountTypes",AccountType.values());
        return "/account/create-account";
    }

    @PostMapping("/create")
    public String insertAccount(@ModelAttribute("account")Account account){
        accountService.createNewAccount(account.getBalance(),new Date(),account.getAccountType(), account.getUserId());
        return "redirect:/index";
    }

}
