package com.example.controller;

import com.example.enums.AccountType;
import com.example.model.Account;
import com.example.repository.AccountRepository;
import com.example.service.AccountService;
import com.example.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Controller
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value={"/index","/"})
    public String getIndexPage (Model model){
        model.addAttribute("accountList",accountService.listAllAccount());
        return "account/index";
    }

    @GetMapping("/create")
    public String getCreateAccount(Model model){
        model.addAttribute("account",Account.builder().build());
        model.addAttribute("accountTypes",AccountType.values());
        return "/account/create-account";
    }

    @PostMapping("/create")
    public String createAccount(@Valid @ModelAttribute("account")Account account, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("accountTypes",AccountType.values());
            return "account/create-account";
        }
        accountService.createNewAccount(account.getBalance(),new Date(),account.getAccountType(), account.getUserId());
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable UUID id){
        accountService.deleteAccount(id);
        return "redirect:/index";
    }

    @GetMapping("/activate/{id}")
    public String activateAccount(@PathVariable UUID id){
        accountService.activateAccount(id);
        return "redirect:/index";

    }

}
