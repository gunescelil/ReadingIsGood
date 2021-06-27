package com.readingisgood.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewBookDto extends JsonObjectBase
{
    @NotBlank(message = "Book Name can not be blank")
    @JsonProperty("bookName")
    private String bookName;
    
    @NotNull
    @Positive(message = "Book price must be above zero")
    @JsonProperty("price")
    private double price;

    public NewBookDto(String bookName, double price)
    {
        super();
        this.bookName = bookName;
        this.price = price;
    }
    
    public NewBookDto()
    {
        super();
    }

    public String getBookName()
    {
        return bookName;
    }

    public void setBookName(String bookName)
    {
        this.bookName = bookName;
    }
    
    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }
}
