package com.marcos.accounts.service.impl;

import com.marcos.accounts.constants.AccountConstants;
import com.marcos.accounts.dto.AccountsDto;
import com.marcos.accounts.dto.CustomerDto;
import com.marcos.accounts.entity.Accounts;
import com.marcos.accounts.entity.Customer;
import com.marcos.accounts.exception.CustomerAlreadyExistsException;
import com.marcos.accounts.exception.ResourceNotFoundException;
import com.marcos.accounts.mapper.AccountsMapper;
import com.marcos.accounts.mapper.CustomerMapper;
import com.marcos.accounts.repository.AccountsRepository;
import com.marcos.accounts.repository.CustomerRepository;
import com.marcos.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    /**
     * Creates a new account using the provided customer details.
     *
     * @param customerDto the data transfer object containing customer information
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> existingCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());

        if(existingCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number " + customer.getMobileNumber());
        }
        Customer customerSaved = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(customerSaved));
    }

    /**
     * Retrieves the account details for the given mobile number.
     *
     * @param mobileNumber the mobile number associated with the customer to be retrieved
     * @return the customer details associated with the given mobile number
     */
    @Override
    public CustomerDto fetchAccountDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() ->
                new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber));

        Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() ->
                new ResourceNotFoundException("Account", "Customer ID", customer.getCustomerId()));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(account, new AccountsDto()));
        return customerDto;
    }

    /**
     * Updates the account details using the provided customer information.
     *
     * @param customerDto the data transfer object containing customer information to be updated
     * @return true if the account was successfully updated, false otherwise
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto != null) {
            Accounts account = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(() ->
                    new ResourceNotFoundException("Account", "Account Number", accountsDto.getAccountNumber()));
            AccountsMapper.mapToAccounts(accountsDto, account);
            accountsRepository.save(account);

            Long custoemrId = account.getCustomerId();
            Customer customer = customerRepository.findById(custoemrId).orElseThrow(() ->
                    new ResourceNotFoundException("Customer", "Customer ID", custoemrId));
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    /**
     * Deletes the account associated with the given mobile number.
     *
     * @param mobileNumber the mobile number associated with the account to be deleted
     * @return true if the account was successfully deleted, false otherwise
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        boolean isDeleted = false;
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() ->
                new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber));
        customerRepository.deleteById(customer.getCustomerId());
        accountsRepository.deleteByCustomerId(customer.getCustomerId());

        isDeleted = true;
        return isDeleted;
    }

    /**
     * Creates a new account using the provided customer information.
     *
     * @param customer the customer details
     * @return the newly created account
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountConstants.SAVINGS);
        newAccount.setBranchAddress(AccountConstants.ADDRESS);
        return newAccount;

    }
}
