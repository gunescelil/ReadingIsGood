package com.readingisgood.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/book")
public class BookController
{
    @GetMapping(value = "/hello", produces = APPLICATION_JSON_VALUE)
    public String home()
    {
        return "Hello Docker World";
    }
}
