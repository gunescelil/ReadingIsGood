package com.readingisgood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.readingisgood.dto.CustomerDto;
import com.readingisgood.dto.CustomerSaveDto;
import com.readingisgood.entity.Customer;
import com.readingisgood.entity.Order;
import com.readingisgood.exception.ExceptionModel;
import com.readingisgood.exception.NotAllowedOperationException;
import com.readingisgood.repository.CustomerRepository;

@Service
public class CustomerService
{
    private CustomerRepository customerRepository;
    
    private OrderService orderService;
    
    //private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    public CustomerService(CustomerRepository customerRepository, OrderService orderService/*, BCryptPasswordEncoder bCryptPasswordEncoder*/) 
    {
        this.customerRepository = customerRepository;
        this.orderService = orderService;
    }
    
    @Transactional(rollbackFor = Exception.class)
    public void saveCustomer(CustomerSaveDto customerDto)
    {
        Customer customer = this.customerRepository.findByEmail(customerDto.getEmail());
        if(customer == null) 
        {
            customer = new Customer(customerDto);
            //customer.setPassword(bCryptPasswordEncoder.encode(customerDto.getPassword()));
            this.customerRepository.save(customer);            
        }
        else
        {
            throw new NotAllowedOperationException(
                    new ExceptionModel(HttpStatus.METHOD_NOT_ALLOWED, "The email with Id: " + customerDto.getEmail() + " is already used!"));
        }
    }
    
    public CustomerDto getCustomer(String email)
    {
        Customer customer =  this.customerRepository.findByEmail(email);
        return new CustomerDto(customer);
    }

    public Page<Order> getOrdersOfCustomer(String email, Pageable pageable)
    {
        return this.orderService.getOrderOfCustomerPageable(email, pageable);
    }

}
