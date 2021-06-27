package com.readingisgood.exception;

public class JsonSerializationException extends RuntimeException
{

    public JsonSerializationException(String message, Throwable e)
    {
        super(message, e);
    }

    /**
     * 
     */
    private static final long serialVersionUID = 3417351000207889169L;

}
