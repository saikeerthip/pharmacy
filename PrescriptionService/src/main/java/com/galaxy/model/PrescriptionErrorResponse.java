package com.galaxy.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrescriptionErrorResponse {

    private String status;
    private String message;
    private long timeStamp;
    private String path;
    private String errorCode;
    private String exception;
    private String error;
}
