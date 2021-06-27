package com.readingisgood.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.readingisgood.dto.JsonObjectBase;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExceptionModel extends JsonObjectBase
{
    /**
     * 
     */
    private static final long serialVersionUID = 4767985021529062954L;

    @JsonProperty("errorCode")
    private HttpStatus errorCode;

    @JsonProperty("errorMessage")
    private String errorMessage;

    public ExceptionModel(HttpStatus errorCode, String errorMessage)
    {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(HttpStatus errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
}