package com.example.converter;

import com.example.dto.AccountDto;
import com.example.service.AccountService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class AccountDtoConvertor implements Converter<String, AccountDto> {
    private final AccountService accountService;

    public AccountDtoConvertor(@Lazy AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public AccountDto convert(String source) {
        if(source.equals("")){
            return null;
        }
       return accountService.findAccountById(Long.parseLong(source));
    }
}
