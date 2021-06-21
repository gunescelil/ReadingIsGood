package com.readingisgood.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.readingisgood.constant.ApplicationConstants;
import com.readingisgood.service.CustomerService;

@RestController
@RequestMapping(value = "/api/customer")
public class CustomerController
{
    private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);
    
    private CustomerService customerService;
    
    @Autowired
    public CustomerController(CustomerService customerService)
    {
        this.customerService = customerService;
    }
    
    @PostMapping(value = "/", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCustomer()
    {
        LOG.debug(ApplicationConstants.STEPIN);
        customerService.saveCustomer();
        LOG.debug(ApplicationConstants.STEPOUT);
    }
    
    @GetMapping(value = "/{customerId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void getOrdersOfCustomer()
    {
        LOG.debug(ApplicationConstants.STEPIN);
        customerService.getOrdersOfCustomer();
        LOG.debug(ApplicationConstants.STEPOUT);
    }
}
