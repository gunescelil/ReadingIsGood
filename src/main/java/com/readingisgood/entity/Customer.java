package com.readingisgood.entity;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.readingisgood.dto.CustomerSaveDto;

public class Customer
{
    @Id
    @Indexed(unique = true)
    @NotBlank(message = "Email is mandatory")
    private String email;
    
    @NotBlank(message = "Password is mandatory")
    private String password;

    private String firstName;

    private String lastName;

    public Customer()
    {
    }

    public Customer(String email, String password, String firstName, String lastName)
    {
        super();
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public Customer(CustomerSaveDto customerDto)
    {
        super();
        this.email = customerDto.getEmail();
        this.password = customerDto.getPassword();
        this.firstName = customerDto.getFirstName();
        this.lastName = customerDto.getLastName();
    }
    
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    @Override
    public String toString()
    {
        return String.format("Customer[firstName='%s', lastName='%s']", firstName, lastName);
    }

}