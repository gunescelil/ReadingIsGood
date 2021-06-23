package com.readingisgood.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewBookDto
{

    @NotBlank(message = "Book Id can not be blank")
    @JsonProperty("bookId")
    private String bookId;

    @NotBlank(message = "Book Name can not be blank")
    @JsonProperty("bookName")
    private String bookName;

    public NewBookDto()
    {
        super();
    }

    public NewBookDto(String bookId, String bookName)
    {
        super();
        this.bookId = bookId;
        this.bookName = bookName;
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
}
