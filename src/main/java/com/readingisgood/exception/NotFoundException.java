package com.readingisgood.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    private final ExceptionModel exceptionModel;
    
    public NotFoundException(ExceptionModel exceptionModel)
    {
        super(exceptionModel.toString());
        this.exceptionModel = exceptionModel;
    }
    
    public ExceptionModel getExceptionModel() 
    {
        return this.exceptionModel;
    }
}
