package com.example.entity;

import com.example.enums.AccountStatus;
import com.example.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
@Entity
public class Account extends BaseEntity{
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Column(columnDefinition = "DATE")
    private Date creationDate;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
}
