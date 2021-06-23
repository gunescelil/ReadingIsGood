package com.readingisgood.exception;

public class NotAllowedOperationException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    private final ExceptionModel exceptionModel;
    
    public NotAllowedOperationException(ExceptionModel exceptionModel)
    {
        super(exceptionModel.toString());
        this.exceptionModel = exceptionModel;
    }
    
    public ExceptionModel getExceptionModel() 
    {
        return this.exceptionModel;
    }
}
