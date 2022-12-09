package com.edurbs.xavantespellingconverter.domain.exception;

public class BusinessException extends RuntimeException{

    private static final long serialVersionUID = 4109182397144340723L;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

}
