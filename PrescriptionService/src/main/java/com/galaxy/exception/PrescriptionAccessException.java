package com.galaxy.exception;

public class PrescriptionAccessException extends RuntimeException {

    private String errorCode;

    public PrescriptionAccessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public PrescriptionAccessException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * @param message the error message
     */
    public PrescriptionAccessException(String message) {
        super(message);
    }

    /**
     * @param cause the exception cause
     */
    public PrescriptionAccessException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message the error message
     * @param cause   the exception cause
     */
    public PrescriptionAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
