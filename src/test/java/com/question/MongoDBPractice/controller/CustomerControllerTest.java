package com.question.MongoDBPractice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.question.MongoDBPractice.domain.Customer;
import com.question.MongoDBPractice.domain.Product;
import com.question.MongoDBPractice.exception.CustomerAlreadyExistsException;
import com.question.MongoDBPractice.service.CustomerServiceImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CustomerServiceImpl customerService;

    @InjectMocks
    private CustomerController customerController;
    private Customer customer1, customer2;
    private Product product1,product2;
    List<Customer> customerList;
    @BeforeEach
    void setUp() {
        product1 = new Product(1,"Nescafe","Coffee");
        customer1 = new Customer(1,"Ajay",985414547,product1);
        product2 = new Product(2,"Bru","Coffee");
        customer2 = new Customer(2,"Harsh",78541254,product2);
        customerList = Arrays.asList(customer1,customer2);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

    }

    @AfterEach
    void tearDown() {
        product1=null;
        product2=null;
        customer1=null;
        customer2=null;
    }
    private static String jsonToString(final Object ob) throws JsonProcessingException {
        String result;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(ob);
            result = jsonContent;
        } catch(JsonProcessingException e) {
            result = "JSON processing error";
        }

        return result;
    }
    @Test
    public void saveCustomer() throws Exception{
        when(customerService.saveCustomer(any())).thenReturn(customer1);
        mockMvc.perform(post("/custData/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(customer1)))
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(customerService,times(1)).saveCustomer(any());

    }
    @Test
    public void savedCustomerFailure() throws Exception {
        when(customerService.saveCustomer(any())).thenThrow(CustomerAlreadyExistsException.class);
        mockMvc.perform(post("/custData/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(customer1)))
                .andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
        verify(customerService,times(1)).saveCustomer(any());
    }
    @Test
    public void deleteCustomer() throws Exception {
        when(customerService.deleteCustomer(anyInt())).thenReturn(true);
        mockMvc.perform(delete("/custData/customer/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(customerService,times(1)).deleteCustomer(anyInt());
    }
}