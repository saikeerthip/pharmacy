package com.galaxy.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrescriptionResponse {

    private String status;
    private String message;
    private Prescription prescription;
}
