package com.galaxy.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Prescription {

    private String patientId;
    private String patientName;
    private Map<String, List<String>> drug;
    private String prescriptionDate;
    private String prescriptionReferenceNumber;
}
