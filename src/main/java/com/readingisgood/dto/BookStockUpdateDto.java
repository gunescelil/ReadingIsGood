package com.readingisgood.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookStockUpdateDto extends JsonObjectBase
{
    @NotBlank(message = "Book Id can not be blank")
    @JsonProperty("bookId")
    private String bookId;

    @NotNull
    @PositiveOrZero(message = "Stock can not be less than zero")
    @JsonProperty("stock")
    private Integer stock;

    public BookStockUpdateDto(@NotBlank(message = "Book Id can not be blank") String bookId,
            @NotBlank(message = "Stock can not be blank") @PositiveOrZero(
                    message = "Stock can not be less than zero") Integer stock)
    {
        super();
        this.bookId = bookId;
        this.stock = stock;
    }

    public BookStockUpdateDto()
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

    public Integer getStock()
    {
        return stock;
    }

    public void setStock(Integer stock)
    {
        this.stock = stock;
    }

}
