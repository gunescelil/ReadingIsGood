package com.readingisgood.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class OrderDto extends JsonObjectBase
{
    @NotBlank
    private String bookId;

    @NotNull
    @PositiveOrZero(message = "Count can not be less than zero")
    private int count;

    @NotBlank
    private String email;

    public OrderDto(String bookId, int count, String email)
    {
        super();
        this.bookId = bookId;
        this.count = count;
        this.email = email;
    }
    
    public OrderDto()
    {
        super();
    }

    public String getBookId()
    {
        return bookId;
    }

    public void setBookId(String bookId)
    {
        this.bookId = bookId;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
