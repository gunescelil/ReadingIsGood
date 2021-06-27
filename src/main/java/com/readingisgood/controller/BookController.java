package com.readingisgood.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.readingisgood.constant.ApplicationConstants;
import com.readingisgood.dto.BookStockUpdateDto;
import com.readingisgood.dto.NewBookDto;
import com.readingisgood.entity.Book;
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

    @PostMapping(value = "", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Book saveNewBook(@RequestBody @Valid NewBookDto bookSaveDto)
    {
        LOG.debug(ApplicationConstants.STEPIN);
        Book result = bookService.saveNewBook(bookSaveDto);
        LOG.debug(ApplicationConstants.STEPOUT);
        return result;
    }

    @PutMapping(value = "", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Book updateBookStock(@RequestBody @Valid BookStockUpdateDto bookStockUpdateDto)
    {
        LOG.debug(ApplicationConstants.STEPIN);
        Book result = bookService.updateStockofBook(bookStockUpdateDto);
        LOG.debug(ApplicationConstants.STEPOUT);
        return result;
    }

    @GetMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public PagedModel<EntityModel<Book>> getAllBooks(final Pageable pageable,
            final PagedResourcesAssembler<Book> pagedAssembler)
    {
        LOG.debug(ApplicationConstants.STEPIN);
        PagedModel<EntityModel<Book>> result = pagedAssembler.toModel(bookService.getAllBooks(pageable));
        LOG.debug(ApplicationConstants.STEPOUT);
        return result;
    }
}
