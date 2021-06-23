package com.readingisgood.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerSaveDto extends CustomerDto
{
    @NotBlank(message = "Password is mandatory")
    @JsonProperty("password")
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
