package com.readingisgood.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.readingisgood.entity.Customer;

public class CustomerDto extends JsonObjectBase
{
    @NotBlank(message = "Email is mandatory")
    @JsonProperty("email")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank
    @JsonProperty("firstName")
    private String firstName;

    @NotBlank
    @JsonProperty("lastName")
    private String lastName;

    public CustomerDto(String email, String firstName, String lastName)
    {
        super();
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public CustomerDto()
    {
        super();
    }

    public CustomerDto(Customer customer)
    {
        super();
        this.email = customer.getEmail();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
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

}
