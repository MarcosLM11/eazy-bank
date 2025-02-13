package com.marcos.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
public class AccountsDto {
    @NotEmpty(message = "Account number can not be empty or null")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must have 10 digits")
    private Long accountNumber;
    @NotEmpty(message = "Account type can not be empty or null")
    private String accountType;
    @NotEmpty(message = "Branch address can not be empty or null")
    private String branchAddress;
}
