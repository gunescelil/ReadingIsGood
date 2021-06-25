package com.readingisgood.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    private final ExceptionModel exceptionModel;
    
    public BadRequestException(ExceptionModel exceptionModel)
    {
        super(exceptionModel.toString());
        this.exceptionModel = exceptionModel;
    }
    
    public ExceptionModel getExceptionModel() 
    {
        return this.exceptionModel;
    }
}