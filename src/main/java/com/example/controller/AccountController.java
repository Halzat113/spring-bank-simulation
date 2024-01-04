package com.example.controller;

import com.example.enums.AccountType;
import com.example.dto.AccountDto;
import com.example.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Controller
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value={"/index"})
    public String getIndexPage (Model model){
        model.addAttribute("accountList",accountService.listAllAccount());
        return "account/index";
    }

    @GetMapping("/create")
    public String getCreateAccount(Model model){
        model.addAttribute("accountDto", new AccountDto());
        model.addAttribute("accountTypes",AccountType.values());
        return "/account/create-account";
    }

    @PostMapping("/create")
    public String createAccount(@Valid @ModelAttribute("accountDto") AccountDto accountDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("accountTypes",AccountType.values());
            return "account/create-account";
        }
        accountService.createNewAccount(accountDto);
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
        return "redirect:/index";
    }

    @GetMapping("/activate/{id}")
    public String activateAccount(@PathVariable Long id){
        accountService.activateAccount(id);
        return "redirect:/index";

    }

}
