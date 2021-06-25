package com.readingisgood.entity;

import javax.validation.constraints.PositiveOrZero;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.readingisgood.dto.NewBookDto;

public class Book
{
    @Id
    @Indexed(unique = true)
    private String bookId;

    private String bookName;

    @PositiveOrZero
    private int stockCount;

    public Book()
    {
    }

    public Book(NewBookDto bookSaveDto)
    {
        super();
        this.bookId = bookSaveDto.getBookId();
        this.bookName = bookSaveDto.getBookName();
    }

    public Book(String bookId, String bookName, int stockCount)
    {
        super();
        this.bookId = bookId;
        this.bookName = bookName;
        this.stockCount = stockCount;
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

    @Override
    public String toString()
    {
        return String.format("CustomerEntity[bookId='%s' firstName='%s', lastName='%s']", bookId, bookName, stockCount);
    }
}
