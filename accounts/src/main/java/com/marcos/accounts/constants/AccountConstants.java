package com.marcos.accounts.constants;


import com.marcos.accounts.controller.AccountsController;

public class AccountConstants {

    private AccountConstants() {
        // Restrict the instantiation of this class
    }

    public static final String SAVINGS = "Savings";
    public static final String ADDRESS = "Address";
    public static final String STATUS_201 = "201";
    public static final String MESSAGE_201 = "Account created successfully";
    public static final String STATUS_200 = "200";
    public static final String MESSAGE_200 = "Request processed successfully";
    public static final String STATUS_400 = "400";
    public static final String MESSAGE_400 = "Bad request";
    public static final String STATUS_404 = "404";
    public static final String MESSAGE_404 = "Account not found";
    public static final String STATUS_500 = "500";
    public static final String MESSAGE_500 = "Internal Server Error";
}
