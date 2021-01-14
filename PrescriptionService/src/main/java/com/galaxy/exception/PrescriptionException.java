package com.galaxy.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class PrescriptionException extends RuntimeException {

    private HttpStatus httpStatus = INTERNAL_SERVER_ERROR;

    private String errorCode;

    public PrescriptionException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public PrescriptionException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public PrescriptionException(String message, HttpStatus status) {
        super(message);
        this.httpStatus = status;
    }

    public PrescriptionException(Throwable e, String message, HttpStatus status) {
        super(message, e);
        this.httpStatus = status;
    }

    /**
     * @param message the error message
     */
    public PrescriptionException(String message) {
        super(message);
    }

    /**
     * @param cause the exception cause
     */
    public PrescriptionException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message the error message
     * @param cause   the exception cause
     */
    public PrescriptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}

