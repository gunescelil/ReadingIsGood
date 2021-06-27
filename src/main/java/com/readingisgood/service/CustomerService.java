package com.readingisgood.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.readingisgood.dto.CustomerDto;
import com.readingisgood.dto.CustomerSaveDto;
import com.readingisgood.entity.Customer;
import com.readingisgood.entity.Order;
import com.readingisgood.exception.ExceptionModel;
import com.readingisgood.exception.NotAllowedOperationException;
import com.readingisgood.exception.NotFoundException;
import com.readingisgood.repository.CustomerRepository;

@Service
public class CustomerService
{
    private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    private CustomerRepository customerRepository;

    private OrderService orderService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, OrderService orderService,
            BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.customerRepository = customerRepository;
        this.orderService = orderService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveCustomer(CustomerSaveDto customerSaveDto)
    {
        Customer customer = this.customerRepository.findByEmail(customerSaveDto.getEmail());
        if (customer == null)
        {
            customer = new Customer(customerSaveDto);
            LOG.info("Customer will be written to DB: email: {}, firstName: {}, lastName: {}", customer.getEmail(),
                    customer.getFirstName(), customer.getLastName());
            customer.setPassword(bCryptPasswordEncoder.encode(customerSaveDto.getPassword()));
            this.customerRepository.save(customer);
        }
        else
        {
            throw new NotAllowedOperationException(new ExceptionModel(HttpStatus.METHOD_NOT_ALLOWED,
                    "The email with Id: " + customerSaveDto.getEmail() + " is already used!"));
        }
    }

    public CustomerDto getCustomer(String email)
    {
        Customer customer = this.customerRepository.findByEmail(email);
        if (customer != null)
        {
            return new CustomerDto(customer);
        }
        else
        {
            throw new NotFoundException(
                    new ExceptionModel(HttpStatus.NOT_FOUND, "The customer with email: " + email + " is not found"));
        }
    }

    public Page<Order> getOrdersOfCustomer(String email, Pageable pageable)
    {
        return this.orderService.getOrderOfCustomerPageable(email, pageable);
    }

}
