package com.readingisgood;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import com.readingisgood.dto.CustomerDto;
import com.readingisgood.dto.CustomerSaveDto;
import com.readingisgood.entity.Customer;
import com.readingisgood.exception.NotAllowedOperationException;
import com.readingisgood.exception.NotFoundException;
import com.readingisgood.repository.CustomerRepository;
import com.readingisgood.service.CustomerService;
import com.readingisgood.service.OrderService;

class CustomerServiceTest
{
    @Mock
    private OrderService orderService;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void init()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavingNewCustomerWorks()
    {
        String firstName = "firstName";
        String lastName = "lastName";
        String email = "email";
        String password = "password";

        CustomerSaveDto customerSaveDto = new CustomerSaveDto(firstName, lastName, email, password);

        when(customerRepository.findByEmail(email)).thenReturn(null);

        customerService.saveCustomer(customerSaveDto);

        ArgumentCaptor<Customer> captorCustomer = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository, times(1)).save(captorCustomer.capture());
        Customer actual = captorCustomer.getValue();

        assertEquals(firstName, actual.getFirstName());
        assertEquals(lastName, actual.getLastName());
        assertEquals(email, actual.getEmail());
        assertEquals(password, actual.getPassword());

    }

    @Test
    void testSavingNewCustomerWillThrowErrorIfAlreadyExistingEmail()
    {
        String firstName = "firstName";
        String lastName = "lastName";
        String email = "existing@email.com";
        String password = "password";

        String expectedMessage = "The email with Id: " + email + " is already used!";
        HttpStatus expectedHttpStatus = HttpStatus.METHOD_NOT_ALLOWED;

        CustomerSaveDto customerSaveDto = new CustomerSaveDto(firstName, lastName, email, password);

        Customer customer = new Customer(firstName, lastName, email, password);
        when(customerRepository.findByEmail(email)).thenReturn(customer);

        NotAllowedOperationException exception = assertThrows(NotAllowedOperationException.class, () ->
        {
            customerService.saveCustomer(customerSaveDto);
        });

        String actualMessage = exception.getMessage();
        assertEquals(exception.getExceptionModel().getErrorCode(), expectedHttpStatus);
        assertTrue(actualMessage.contains(expectedMessage));

        ArgumentCaptor<Customer> captorCustomer = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository, never()).save(captorCustomer.capture());
    }

    @Test
    void testFindCustomerWorks()
    {
        String firstName = "firstName";
        String lastName = "lastName";
        String email = "existing@email.com";
        String password = "password";
        Customer customer = new Customer(email, password, firstName, lastName);

        when(customerRepository.findByEmail(email)).thenReturn(customer);

        CustomerDto actual = customerService.getCustomer(email);

        verify(customerRepository, Mockito.times(1)).findByEmail(email);

        assertEquals(actual.getEmail(), email);
        assertEquals(actual.getFirstName(), firstName);
        assertEquals(actual.getLastName(), lastName);
    }

    @Test
    void testThrowsNotFoundIfCustomerIsNotPresent()
    {
        String email = "existing@email.com";

        String expectedMessage = "The customer with email: " + email + " is not found";
        HttpStatus expectedHttpStatus = HttpStatus.NOT_FOUND;

        when(customerRepository.findByEmail(email)).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
        {
            customerService.getCustomer(email);
        });

        String actualMessage = exception.getMessage();
        assertEquals(exception.getExceptionModel().getErrorCode(), expectedHttpStatus);
        assertTrue(actualMessage.contains(expectedMessage));

        verify(customerRepository, Mockito.times(1)).findByEmail(email);
    }

    @Test
    void testGetOrderOfCustomerPageableWorks()
    {
        String email = "gunescelil@gmail.com";
        Pageable pageable = PageRequest.of(0, 5);

        customerService.getOrdersOfCustomer(email, pageable);

        ArgumentCaptor<String> captorEmail = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Pageable> captorPageable = ArgumentCaptor.forClass(Pageable.class);

        verify(orderService, Mockito.times(1)).getOrderOfCustomerPageable(captorEmail.capture(),
                captorPageable.capture());

        assertEquals(captorEmail.getValue(), email);
        assertEquals(captorPageable.getValue(), pageable);

    }

}
