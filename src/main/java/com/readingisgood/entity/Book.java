package com.readingisgood.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.readingisgood.dto.NewBookDto;

public class Book
{
    @Id
    @Indexed(unique = true)
    private String bookId;

    @NotBlank
    private String bookName;

    @PositiveOrZero
    private int stockCount;

    @Positive
    private double price;

    public Book()
    {
    }

    public Book(NewBookDto bookSaveDto)
    {
        super();
        this.bookName = bookSaveDto.getBookName();
        this.price = bookSaveDto.getPrice();
    }

    public Book(String bookId, String bookName, int stockCount, double price)
    {
        super();
        this.bookId = bookId;
        this.bookName = bookName;
        this.stockCount = stockCount;
        this.price = price;
    }

    public String getBookId()
    {
        return bookId;
    }

    public void setBookId(String bookId)
    {
        this.bookId = bookId;
    }

    public String getBookName()
    {
        return bookName;
    }

    public void setBookName(String bookName)
    {
        this.bookName = bookName;
    }

    public int getStockCount()
    {
        return stockCount;
    }

    public void setStockCount(int stockCount)
    {
        this.stockCount = stockCount;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    @Override
    public String toString()
    {
        return String.format("Book[bookId='%s' bookName='%s', stockCount='%s']", bookId, bookName, stockCount);
    }
}
