package com.marcos.accounts.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CustomerDto {
    @NotEmpty(message = "Name can not be empty or null")
    @Size(min = 5, max = 30, message = "Name should have between 5 and 30 characters")
    private String name;
    @NotEmpty(message = "Email can not be empty or null")
    @Email(message = "Invalid email address")
    private String email;
    @Pattern(regexp = "(^$|[0-9]{9})", message = "Mobile number must have 9 digits")
    private String mobileNumber;
    private AccountsDto accountsDto;
}
