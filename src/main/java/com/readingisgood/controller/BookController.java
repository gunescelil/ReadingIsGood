package com.readingisgood.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.readingisgood.constant.ApplicationConstants;
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

    @PostMapping(value = "/", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void saveBook(@RequestBody String name)
    {
        LOG.debug(ApplicationConstants.STEPIN);
        bookService.saveBook(name);
        LOG.debug(ApplicationConstants.STEPOUT);
    }
    
    @PatchMapping(value = "/", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateBook(@RequestBody String name)
    {
        LOG.debug(ApplicationConstants.STEPIN);
        bookService.updateBook(name);
        LOG.debug(ApplicationConstants.STEPOUT);
    }
}
