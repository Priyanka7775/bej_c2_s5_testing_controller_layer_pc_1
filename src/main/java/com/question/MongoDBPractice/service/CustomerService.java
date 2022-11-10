package com.question.MongoDBPractice.service;

import com.question.MongoDBPractice.domain.Customer;
import com.question.MongoDBPractice.exception.CustomerAlreadyExistsException;
import com.question.MongoDBPractice.exception.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {

    Customer saveCustomer(Customer customer) throws CustomerAlreadyExistsException;
    List<Customer> getAllCustomers() throws Exception;
    boolean deleteCustomer(int customerId) throws CustomerNotFoundException;
    List<Customer> getAllCustomersByProductName(String productName) throws CustomerNotFoundException;
}
