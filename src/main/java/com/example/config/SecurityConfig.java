package com.example.config;

import com.example.service.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    private final SecurityService securityService;
    private final AuthSuccessHandler authSuccessHandler;

    public SecurityConfig(SecurityService securityService, AuthSuccessHandler authSuccessHandler) {
        this.securityService = securityService;
        this.authSuccessHandler = authSuccessHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return  http
                .authorizeRequests()
                .antMatchers("/account/**").hasAnyAuthority("Admin")
                .antMatchers("/transaction/**").hasAnyAuthority("Admin","Cashier")
                .antMatchers("/login","/")
                .permitAll()
              .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(authSuccessHandler) //landing pages for admin and cashier
                .failureUrl("/login?error=true")
                .permitAll()
              .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
              .and()
                .rememberMe()
                .tokenValiditySeconds(300)
                .key("bankapp")
                .userDetailsService(securityService)
                .and().build();
    }
}
