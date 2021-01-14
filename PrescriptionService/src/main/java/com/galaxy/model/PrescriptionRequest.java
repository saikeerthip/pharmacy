package com.galaxy.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class PrescriptionRequest {

    @NotBlank(message = "patientId is invalid")
    private String patientId;
    @NotBlank(message = "patientName is invalid")
    private String patientName;
    @NotEmpty(message = "drug is invalid")
    private List<String> drug;
}
