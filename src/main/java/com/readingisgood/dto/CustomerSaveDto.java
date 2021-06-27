package com.readingisgood.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerSaveDto extends CustomerDto
{
    /**
     * 
     */
    private static final long serialVersionUID = 2222541347362263004L;
    @NotBlank(message = "Password is mandatory")
    @JsonProperty("password")
    @Length(min = 8, max = 30, message = "Password length must be between 8 and 30")
    // Can be added more check with regex and so on.
    private String password;

    public CustomerSaveDto(String firstName, String lastName, String email, String password)
    {
        super(email, firstName, lastName);
        this.password = password;
    }

    public CustomerSaveDto()
    {
        super();
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

}
