package com.readingisgood.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.readingisgood.constant.OrderStatus;

public class Order
{
    @Id
    @Indexed(unique = true)
    private String id;

    private long orderDate;

    @NotBlank
    private String bookId;

    @PositiveOrZero(message = "Count can not be less than zero")
    private int count;

    private OrderStatus status;

    @NotBlank
    private String email;

    @Positive
    private double bookPriceAtDate;

    public Order(String id, long orderDate, String bookId, int count, OrderStatus status, String email,
            double bookPriceAtDate)
    {
        super();
        this.id = id;
        this.orderDate = orderDate;
        this.bookId = bookId;
        this.count = count;
        this.status = status;
        this.email = email;
        this.bookPriceAtDate = bookPriceAtDate;
    }

    public Order()
    {
        super();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public long getOrderDate()
    {
        return orderDate;
    }

    public void setOrderDate(long orderDate)
    {
        this.orderDate = orderDate;
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

    public OrderStatus getStatus()
    {
        return status;
    }

    public void setStatus(OrderStatus status)
    {
        this.status = status;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public double getBookPriceAtDate()
    {
        return bookPriceAtDate;
    }

    public void setBookPriceAtDate(double bookPriceAtDate)
    {
        this.bookPriceAtDate = bookPriceAtDate;
    }

}
