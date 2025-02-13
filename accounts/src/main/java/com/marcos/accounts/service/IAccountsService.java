package com.marcos.accounts.service;

import com.marcos.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     * Creates a new account using the provided customer details.
     *
     * @param customerDto the data transfer object containing customer information
     */
    void createAccount(CustomerDto customerDto);

    /**
     * Retrieves the account details for the given mobile number.
     *
     * @param mobileNumber the mobile number associated with the customer to be retrieved
     * @return the customer details associated with the given mobile number
     */
    CustomerDto fetchAccountDetails(String mobileNumber);

    /**
     * Updates the account details using the provided customer information.
     *
     * @param customerDto the data transfer object containing customer information to be updated
     * @return true if the account was successfully updated, false otherwise
     */
    boolean updateAccount(CustomerDto customerDto);

    /**
     * Deletes the account associated with the given mobile number.
     *
     * @param mobileNumber the mobile number associated with the account to be deleted
     * @return true if the account was successfully deleted, false otherwise
     */
    boolean deleteAccount(String mobileNumber);
}
