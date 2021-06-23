package com.readingisgood.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.readingisgood.constant.ApplicationConstants;
import com.readingisgood.dto.BookStockUpdateDto;
import com.readingisgood.dto.NewBookDto;
import com.readingisgood.service.BookService;

@RestController
@RequestMapping(value = "/api/book")
public class BookController
{
    private static final Logger LOG = LoggerFactory.getLogger(BookController.class);

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService)
    {
        this.bookService = bookService;
    }

    @PostMapping(value = "", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void saveNewBook(@RequestBody @Valid NewBookDto bookSaveDto)
    {
        LOG.debug(ApplicationConstants.STEPIN);
        bookService.saveNewBook(bookSaveDto);
        LOG.debug(ApplicationConstants.STEPOUT);
    }
    
    @PutMapping(value = "", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateBookStock(@RequestBody @Valid BookStockUpdateDto bookStockUpdateDto)
    {
        LOG.debug(ApplicationConstants.STEPIN);
        bookService.updateStockofBook(bookStockUpdateDto);
        LOG.debug(ApplicationConstants.STEPOUT);
    }
}
